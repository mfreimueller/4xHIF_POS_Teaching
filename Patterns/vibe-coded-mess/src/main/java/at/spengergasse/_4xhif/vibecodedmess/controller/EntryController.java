package at.spengergasse._4xhif.vibecodedmess.controller;

import at.spengergasse._4xhif.vibecodedmess.dto.EntryRequest;
import at.spengergasse._4xhif.vibecodedmess.model.Country;
import at.spengergasse._4xhif.vibecodedmess.model.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles everything related to European Singing Championship entries.
 * And I do mean everything.
 * This class and I have been through a lot together.
 * I am not sure either of us is okay.
 */
@RestController
@RequestMapping("/api/entries")
public class EntryController {

    // Funny story: there used to be other layers between this
    // and the database. The client said to simplify things.
    @PersistenceContext
    private EntityManager em;

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    @PostMapping
    @Transactional
    public ResponseEntity<Entry> createEntry(@RequestBody EntryRequest request) {

        // Fetching the country here felt wrong when I wrote it.
        // It still feels wrong now. And yet, here we are.
        Country country = em.find(Country.class, request.countryId());
        if (country == null) {
            return ResponseEntity.badRequest().build();
        }

        // Eleven arguments walk into a constructor...
        // There is no punchline. Only this.
        Entry entry = new Entry(
                request.songTitle(),
                request.artistName(),
                request.performanceLanguage(),
                request.runningOrder(),
                request.durationSeconds(),
                request.qualifiedForFinal(),
                request.firstTimeParticipant(),
                request.stageName(),
                0,
                0,
                country
        );

        em.persist(entry);
        return ResponseEntity.ok(entry);
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------

    @GetMapping
    public List<Entry> getAllEntries() {
        return em.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable Long id) {
        Entry entry = em.find(Entry.class, id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entry);
    }

    @GetMapping("/country/{countryId}")
    public List<Entry> getEntriesByCountry(@PathVariable Long countryId) {
        // I wrote this query at 11pm. It works.
        // I have chosen not to think too hard about where it lives.
        return em.createQuery(
                        "SELECT e FROM Entry e WHERE e.country.id = :countryId", Entry.class)
                .setParameter("countryId", countryId)
                .getResultList();
    }

    @GetMapping("/finalists")
    public List<Entry> getFinalists() {
        return em.createQuery(
                        "SELECT e FROM Entry e WHERE e.qualifiedForFinal = true ORDER BY e.runningOrder",
                        Entry.class)
                .getResultList();
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id,
                                             @RequestBody EntryRequest request) {
        Entry entry = em.find(Entry.class, id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }

        Country country = em.find(Country.class, request.countryId());
        if (country == null) {
            return ResponseEntity.badRequest().build();
        }

        // Setting fields one by one.
        // A soothing, meditative process.
        // Totally not something that scales poorly with every new field.
        entry.setSongTitle(request.songTitle());
        entry.setArtistName(request.artistName());
        entry.setPerformanceLanguage(request.performanceLanguage());
        entry.setRunningOrder(request.runningOrder());
        entry.setDurationSeconds(request.durationSeconds());
        entry.setQualifiedForFinal(request.qualifiedForFinal());
        entry.setFirstTimeParticipant(request.firstTimeParticipant());
        entry.setStageName(request.stageName());
        entry.setCountry(country);

        em.merge(entry);
        return ResponseEntity.ok(entry);
    }

    @PatchMapping("/{id}/qualify")
    @Transactional
    public ResponseEntity<Entry> qualifyForFinal(@PathVariable Long id) {
        Entry entry = em.find(Entry.class, id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        entry.setQualifiedForFinal(true);
        em.merge(entry);
        return ResponseEntity.ok(entry);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        Entry entry = em.find(Entry.class, id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        em.remove(entry);
        return ResponseEntity.noContent().build();
    }
}
