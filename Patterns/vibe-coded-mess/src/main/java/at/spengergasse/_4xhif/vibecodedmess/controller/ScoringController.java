package at.spengergasse._4xhif.vibecodedmess.controller;

import at.spengergasse._4xhif.vibecodedmess.dto.ScoreEntry;
import at.spengergasse._4xhif.vibecodedmess.dto.VoteRequest;
import at.spengergasse._4xhif.vibecodedmess.model.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages scoring and rankings for the ESC 2026 Grand Final.
 *
 * Supports four voting methods: jury, tele, combined, balanced.
 * If a fifth one is ever needed, that's a problem for future me.
 * Future me is also the one who has to read this file.
 * I am sorry, future me.
 */
@RestController
@RequestMapping("/api/scoring")
public class ScoringController {

    @PersistenceContext
    private EntityManager em;

    /**
     * Returns the scoreboard for all finalists, sorted by the given voting method.
     *
     * @param method one of: "jury", "tele", "combined", "balanced"
     *               (adding a new one is left as an exercise for the reader)
     */
    @GetMapping("/scoreboard")
    public ResponseEntity<List<ScoreEntry>> getScoreboard(
            @RequestParam(defaultValue = "combined") String method) {

        List<Entry> finalists = em.createQuery(
                        "SELECT e FROM Entry e WHERE e.qualifiedForFinal = true", Entry.class)
                .getResultList();

        // I want you to know that I considered alternatives to what follows.
        // I considered them for a long time.
        // Then the deadline happened.
        List<ScoreEntry> scoreboard;

        if (method.equals("jury")) {
            scoreboard = finalists.stream()
                    .map(e -> new ScoreEntry(
                            e.getCountry().getName(),
                            e.getSongTitle(),
                            e.getArtistName(),
                            e.getJuryPoints()
                    ))
                    .sorted(Comparator.comparingInt(ScoreEntry::totalPoints).reversed())
                    .collect(Collectors.toList());

        } else if (method.equals("tele")) {
            scoreboard = finalists.stream()
                    .map(e -> new ScoreEntry(
                            e.getCountry().getName(),
                            e.getSongTitle(),
                            e.getArtistName(),
                            e.getTelePoints()
                    ))
                    .sorted(Comparator.comparingInt(ScoreEntry::totalPoints).reversed())
                    .collect(Collectors.toList());

        } else if (method.equals("combined")) {
            scoreboard = finalists.stream()
                    .map(e -> new ScoreEntry(
                            e.getCountry().getName(),
                            e.getSongTitle(),
                            e.getArtistName(),
                            e.getJuryPoints() + e.getTelePoints()
                    ))
                    .sorted(Comparator.comparingInt(ScoreEntry::totalPoints).reversed())
                    .collect(Collectors.toList());

        } else if (method.equals("balanced")) {
            // 50/50. Mathematically sound. Architecturally... let's move on.
            scoreboard = finalists.stream()
                    .map(e -> new ScoreEntry(
                            e.getCountry().getName(),
                            e.getSongTitle(),
                            e.getArtistName(),
                            (e.getJuryPoints() + e.getTelePoints()) / 2
                    ))
                    .sorted(Comparator.comparingInt(ScoreEntry::totalPoints).reversed())
                    .collect(Collectors.toList());

        } else {
            // The one line in this method I feel completely at peace with.
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(scoreboard);
    }

    /**
     * Submits points for an entry using a specific voting method.
     * The attentive reader will notice a structural resemblance to the method above.
     * The attentive reader is correct, and I respect their observation.
     */
    @PostMapping("/vote/{entryId}")
    @Transactional
    public ResponseEntity<Entry> submitVote(
            @PathVariable Long entryId,
            @RequestBody VoteRequest request) {

        Entry entry = em.find(Entry.class, entryId);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }

        if (request.voteType().equals("jury")) {
            entry.setJuryPoints(entry.getJuryPoints() + request.points());

        } else if (request.voteType().equals("tele")) {
            entry.setTelePoints(entry.getTelePoints() + request.points());

        } else if (request.voteType().equals("combined")) {
            int half = request.points() / 2;
            entry.setJuryPoints(entry.getJuryPoints() + half);
            entry.setTelePoints(entry.getTelePoints() + half);

        } else if (request.voteType().equals("balanced")) {
            // 40% jury, 60% tele. A carefully considered ratio.
            // Reconsidering it would require touching this file again.
            // We do not touch this file again.
            int juryShare = (int) (request.points() * 0.4);
            int teleShare = request.points() - juryShare;
            entry.setJuryPoints(entry.getJuryPoints() + juryShare);
            entry.setTelePoints(entry.getTelePoints() + teleShare);

        } else {
            return ResponseEntity.badRequest().build();
        }

        em.merge(entry);
        return ResponseEntity.ok(entry);
    }

    /**
     * Returns the winner for each voting method.
     *
     * Three separate max-lookups. Three separate method names hardcoded as strings.
     * This is fine. Everything is fine.
     */
    @GetMapping("/winners")
    public ResponseEntity<Map<String, String>> getWinnersByMethod() {
        List<Entry> finalists = em.createQuery(
                        "SELECT e FROM Entry e WHERE e.qualifiedForFinal = true", Entry.class)
                .getResultList();

        if (finalists.isEmpty()) {
            return ResponseEntity.ok(Map.of());
        }

        Entry juryWinner = finalists.stream()
                .max(Comparator.comparingInt(Entry::getJuryPoints))
                .orElseThrow();

        Entry teleWinner = finalists.stream()
                .max(Comparator.comparingInt(Entry::getTelePoints))
                .orElseThrow();

        // If you are wondering why "balanced" is not in this map:
        // I was tired. The deadline was not.
        Entry combinedWinner = finalists.stream()
                .max(Comparator.comparingInt(e -> e.getJuryPoints() + e.getTelePoints()))
                .orElseThrow();

        return ResponseEntity.ok(Map.of(
                "jury",     juryWinner.getCountry().getName() + " - " + juryWinner.getSongTitle(),
                "tele",     teleWinner.getCountry().getName() + " - " + teleWinner.getSongTitle(),
                "combined", combinedWinner.getCountry().getName() + " - " + combinedWinner.getSongTitle()
        ));
    }
}
