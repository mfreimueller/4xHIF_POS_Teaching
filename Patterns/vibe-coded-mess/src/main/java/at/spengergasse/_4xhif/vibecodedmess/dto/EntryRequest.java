package at.spengergasse._4xhif.vibecodedmess.dto;

public record EntryRequest(
        String songTitle,
        String artistName,
        String performanceLanguage,
        Integer runningOrder,
        int durationSeconds,
        boolean qualifiedForFinal,
        boolean firstTimeParticipant,
        String stageName,
        Long countryId
) {
}
