//@@author JeraldChen
package seedu.duke.ui;

import seedu.duke.Duke;
import seedu.duke.diagnosis.IllnessMatch;
import seedu.duke.diagnosis.symptoms.Symptom;
import seedu.duke.medicine.MedicineManager;
import seedu.duke.patient.Patient;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

import static seedu.duke.save.Storage.saveData;

public class Parser {

    /**
     * Parses the user input for the main menu.
     * @param choice Users choice of input.
     */
    public static void parseWelcome(String choice) {
        assert choice != null : "Choice cannot be null";
        switch (choice) {
        case "1":
            Menu.register();
            break;
        case "2":
            Menu.login();
            break;
        case "3":
            Menu.exit();
            break;
        default:
            System.out.println("Invalid input!");
            break;
        }
    }

    //@@author Thunderdragon221, Geeeetyx, tanyizhe
    /**
     * Parses the user input for the account menu.
     *
     * @param choice Users choice of input.
    */
    public static void parseAccountCommand(String choice) {
        assert choice != null : "Choice cannot be null";
        Patient user = Information.getPatientInfo(Duke.getPassword());
        MedicineManager medicineManager = new MedicineManager();

        switch (choice) {
        case "1":
            //@@author tanyizhe
            ArrayList<Symptom> symptoms = Menu.getUserSymptoms();
            Menu.displayPossibleIllness(symptoms);
            ArrayList<IllnessMatch> possibleIllnesses = medicineManager.analyseIllness(symptoms);
            for (IllnessMatch illnessMatch : possibleIllnesses) {
                user.updatePatientDiagnosisHistory(illnessMatch.getIllness().getIllnessName());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                ArrayList<String> medicineArrayList = medicineManager
                            .getRelevantMedicationInString(illnessMatch.getIllness().getIllnessName());
                if (!(medicineArrayList == null)) {
                    user.updatePatientMedicineHistory(dtf.format(now), medicineArrayList);
                }
            }
            saveData();
            break;
        //@@author Thunderdragon221
        case "2":
            Information.printDiagnosisHistory(Duke.getPassword());
            break;
        case "3":
            Information.resetDiagnosisHistory(Duke.getPassword());
            break;
        case "4":
            Information.resetSymptomChoice(Menu.symptoms);
            break;
        //@@author Geeeetyx
        case "5":
            user.printPatientMedicineHistory();
            break;
        case "6":
            Menu.exit();
            break;
        default:
            System.out.println("Invalid input!");
            break;
        }
    }
}
