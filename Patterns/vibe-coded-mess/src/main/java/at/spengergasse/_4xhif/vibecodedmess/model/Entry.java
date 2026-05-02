package at.spengergasse._4xhif.vibecodedmess.model;

import jakarta.persistence.*;

/**
 * Represents a country's entry in the Eurovision Song Contest 2026.
 *
 * A perfectly normal class. Nothing to see here.
 * Please do not count the constructor arguments.
 */
@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String songTitle;

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private String performanceLanguage;

    // 1-26, assigned after the draw. null until then. Living on the edge.
    @Column
    private Integer runningOrder;

    @Column(nullable = false)
    private int durationSeconds;

    @Column(nullable = false)
    private boolean qualifiedForFinal;

    // The one next to it is also a boolean. They look identical. I see no issue.
    @Column(nullable = false)
    private boolean firstTimeParticipant;

    @Column
    private String stageName;

    @Column(nullable = false)
    private int juryPoints;

    @Column(nullable = false)
    private int telePoints;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Entry() {}

    // I counted the parameters so you don't have to. There are eleven.
    // You're welcome.
    public Entry(String songTitle, String artistName, String performanceLanguage,
                 Integer runningOrder, int durationSeconds, boolean qualifiedForFinal,
                 boolean firstTimeParticipant, String stageName,
                 int juryPoints, int telePoints, Country country) {
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.performanceLanguage = performanceLanguage;
        this.runningOrder = runningOrder;
        this.durationSeconds = durationSeconds;
        this.qualifiedForFinal = qualifiedForFinal;
        this.firstTimeParticipant = firstTimeParticipant;
        this.stageName = stageName;
        this.juryPoints = juryPoints;
        this.telePoints = telePoints;
        this.country = country;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSongTitle() { return songTitle; }
    public void setSongTitle(String songTitle) { this.songTitle = songTitle; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getPerformanceLanguage() { return performanceLanguage; }
    public void setPerformanceLanguage(String performanceLanguage) { this.performanceLanguage = performanceLanguage; }

    public Integer getRunningOrder() { return runningOrder; }
    public void setRunningOrder(Integer runningOrder) { this.runningOrder = runningOrder; }

    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }

    public boolean isQualifiedForFinal() { return qualifiedForFinal; }
    public void setQualifiedForFinal(boolean qualifiedForFinal) { this.qualifiedForFinal = qualifiedForFinal; }

    public boolean isFirstTimeParticipant() { return firstTimeParticipant; }
    public void setFirstTimeParticipant(boolean firstTimeParticipant) { this.firstTimeParticipant = firstTimeParticipant; }

    public String getStageName() { return stageName; }
    public void setStageName(String stageName) { this.stageName = stageName; }

    public int getJuryPoints() { return juryPoints; }
    public void setJuryPoints(int juryPoints) { this.juryPoints = juryPoints; }

    public int getTelePoints() { return telePoints; }
    public void setTelePoints(int telePoints) { this.telePoints = telePoints; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
}
