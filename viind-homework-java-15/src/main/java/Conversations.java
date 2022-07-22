import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Conversations {
    private long latest_event_time;
    private String[] intents;
    private String[] actions;
    private float minimum_action_confidence;
    private float maximum_action_confidence;
    private float minimum_intent_confidence;
    private float maximum_intent_confidence;
    private boolean in_training_data;
    private String review_status;
    private String[] policies;
    private int n_user_messages;
    private boolean has_flagged_messages;
    private String[] corrected_messages;
    private boolean interactive;
    private String[] tags;
    private String created_by;

    public Conversations(){

    }

}
