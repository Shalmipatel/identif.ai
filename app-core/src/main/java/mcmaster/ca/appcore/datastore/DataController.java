package mcmaster.ca.appcore.datastore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DataController {
    public static final int MAX_RESULTS_FOR_EXPERT = 5;
    public static final String RESULTS_PARAM = "PeopleResultsPARAM";
    private final ArrayList<ActorModel> results = new ArrayList<>();

    public DataController() {
        // Intentionally left empty.
    }

    /**
     * Retrieve a list of people results in the correct order.
     *
     * @return List of people results.
     */
    public ArrayList<ActorModel> processResults() {
        Collections.sort(results);
        return results;
    }

    /**
     * Called when a new set of people results are received.
     *
     * @param actorModels
     *     The new list of people results.
     */
    public void onReceivedPeopleResults(List<ActorModel> actorModels) {
        for (ActorModel person : actorModels) {
            // If the person is already in the list, increase the score.
            if (results.contains(person)) {
                results.get(results.indexOf(person)).increaseScore(person.score);
            } else {
                // If not in the list, just append to the end.
                results.add(person);
            }
        }
    }
}
