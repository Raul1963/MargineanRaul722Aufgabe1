import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static class FallAkten {
        int id;
        String patient;

        String symptom;
        String diagnose;
        String date;
        String krankenhaus;

        public FallAkten(int id, String patient, String symptom, String diagnose, String date, String krankenhaus) {
            this.id = id;
            this.patient = patient;
            this.symptom = symptom;
            this.diagnose = diagnose;
            this.date = date;
            this.krankenhaus = krankenhaus;
        }

        @Override
        public String toString() {
            return "FallAkten{" +
                    "id=" + id +
                    ", patient='" + patient + '\'' +
                    ", symptom='" + symptom + '\'' +
                    ", diagnose='" + diagnose + '\'' +
                    ", date=" + date +
                    ", krankenhaus='" + krankenhaus + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        // Datei einlesen
        List<FallAkten> eintraege = lesenDatei("src/fallakten.tsv");
        System.out.println(eintraege);
        // Aufgabe b: Studierende mit einem bestimmten Anfangsbuchstaben anzeigen
}

    // Methode zum Einlesen der Datei
    public static List<FallAkten> lesenDatei(String dateiname) throws IOException {
        List<FallAkten> eintraege = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(dateiname));
        System.out.println(lines);
        for (int i=1; i<lines.size();i++) {
            String[] felder = lines.get(i).split("\\s+");
            int id = Integer.parseInt(felder[0].trim());
            String patient = felder[1].trim() + " " + felder[2].trim(); // Combining first and last name
            String symptom = felder[3].trim();
            String diagnose = felder[3].trim();
            String date =  felder[4].trim();
            int j =5;
            String krankenhaus = null;
            while(j<felder.length) {
                krankenhaus += krankenhaus + felder[j].trim();
                j++;
            }
            eintraege.add(new FallAkten(id, patient, patient, diagnose, date,krankenhaus));
        }
        return eintraege;
    }


}