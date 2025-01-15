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
        List<FallAkten> eintraege = lesenDatei("src/fallakten.tsv");;
        // Aufgabe b: Studierende mit einem bestimmten Anfangsbuchstaben anzeigen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Geben Sie einen Großbuchstaben ein: ");
        char buchstabe = scanner.next().charAt(0);
        anzeigenPatientenMitNameBeginnendMitLetter(eintraege, buchstabe);
        speichernAnzahlFalle(eintraege, "src/fallanzahl.txt");
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
            String diagnose = felder[4].trim();
            String date =  felder[5].trim();
            int j =6;
            String krankenhaus = felder[6].trim();
//            while(j<felder.length) {
//                krankenhaus += krankenhaus + felder[j].trim();
//                j++;
//            }
            eintraege.add(new FallAkten(id, patient, symptom, diagnose, date,krankenhaus));
        }
        return eintraege;
    }

    public static void anzeigenPatientenMitNameBeginnendMitLetter(List<FallAkten> eintraege, char buchstabe) {
        eintraege.stream()
                .map(e -> e.patient)
                .filter(name -> name.startsWith(String.valueOf(buchstabe)))
                .distinct()
                .forEach(System.out::println);
    }

    // Methode für Aufgabe c
//    public static void anzeigenPatientenMIFeiber(List<FallAkten> eintraege) {
//         List<FallAkten> fl =   eintraege.stream().filter(e -> e.symptom == "Fieber").sorted(e->e.date). toList();
//
//    }

    // Methode für Aufgabe d
    public static void speichernAnzahlFalle(List<FallAkten> eintraege, String dateiname) throws IOException {
        Map<String, Integer> falleProKrankenhaus = new HashMap<>();
        for (FallAkten e : eintraege) {
            falleProKrankenhaus.put(e.krankenhaus, 0);
        }
        System.out.println(falleProKrankenhaus);
        for (FallAkten eintrag : eintraege) {
            falleProKrankenhaus.put(eintrag.krankenhaus, falleProKrankenhaus.get(eintrag.krankenhaus) + 1);
        }

        List<String> ergebnisZeilen = falleProKrankenhaus.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(entry -> entry.getKey() + "$" + entry.getValue())
                .collect(Collectors.toList());

        Files.write(Paths.get(dateiname), ergebnisZeilen);
    }
}