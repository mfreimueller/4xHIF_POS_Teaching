package at.spengergasse._4xhif.vibecodedmess;

import at.spengergasse._4xhif.vibecodedmess.model.Country;
import at.spengergasse._4xhif.vibecodedmess.model.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Populates the H2 database with sample European Singing Championship 2026 entries on startup.
 *
 * This class contains the most honest representation of my inner state
 * during the development of this project.
 * The constructor calls below are that representation.
 */
@Component
public class DataInitializer {

    @PersistenceContext
    private EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        // --- Countries ---
        Country austria  = new Country("Austria",     "AT");
        Country sweden   = new Country("Sweden",      "SE");
        Country italy    = new Country("Italy",       "IT");
        Country ukraine  = new Country("Ukraine",     "UA");
        Country portugal = new Country("Portugal",    "PT");
        Country germany  = new Country("Germany",     "DE");

        em.persist(austria);
        em.persist(sweden);
        em.persist(italy);
        em.persist(ukraine);
        em.persist(portugal);
        em.persist(germany);

        // --- Entries ---
        // What follows is technically correct Java.
        // I stand by that and only that.

        Entry e1 = new Entry(
                "The Sound of Vienna",  // argument 1
                "Amadeus Kraft",        // argument 2
                "German",               // argument 3
                3,                      // argument 4
                183,                    // argument 5
                true,                   // argument 6
                false,                  // argument 7 – different from argument 6, I promise
                "A.K.",                 // argument 8
                210,                    // argument 9
                185,                    // argument 10
                austria                 // argument 11, the finish line
        );

        Entry e2 = new Entry(
                "Northern Lights",
                "Saga Lindqvist",
                "English",
                7,
                196,
                true,
                false,
                null,   // I know what this null means. You will have to trust me on that.
                240,
                260,
                sweden
        );

        Entry e3 = new Entry(
                "Volare ancora",
                "Marco Ferretti",
                "Italian",
                12,
                178,
                true,
                false,
                "Il Falco",
                195,
                230,
                italy
        );

        Entry e4 = new Entry(
                "Незламна",
                "Kalyna",
                "Ukrainian",
                15,
                201,
                true,
                false,
                null,   // a different null, for different reasons, in the same position
                185,
                312,
                ukraine
        );

        Entry e5 = new Entry(
                "Saudade do Futuro",
                "Beatriz Melo",
                "Portuguese",
                9,
                192,
                false,
                true,   // I did not swap these. Please believe me.
                null,
                0,
                0,
                portugal
        );

        Entry e6 = new Entry(
                "Comeback",
                "Die Funken",
                "English",
                1,
                188,
                false,
                false,
                null,
                0,
                0,
                germany
        );

        em.persist(e1);
        em.persist(e2);
        em.persist(e3);
        em.persist(e4);
        em.persist(e5);
        em.persist(e6);
    }
}
