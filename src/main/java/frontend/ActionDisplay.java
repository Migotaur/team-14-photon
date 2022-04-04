package frontend;
import javax.swing.JFrame;
import backend.UDPHandler;
import java.util.ArrayList;
import java.util.Random;
import backend.Player;
import javax.swing.JLabel;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class ActionDisplay extends javax.swing.JPanel{

    ArrayList<Player> players;
    ArrayList<JLabel> red_players = new ArrayList<JLabel>();
    ArrayList<JLabel> green_players = new ArrayList<JLabel>();
    
    Player attacker = null;
    Player target = null;
    Player top_scorer = null;
    UDPHandler handler;
    Timer blink_timer;

    int redScore = 0;
    int greenScore = 0;
    int event_counter = 0;
    public static void main(String[] args) {
        ArrayList<backend.Player> test_players = new ArrayList<Player>();
        JFrame frame = new JFrame();
        ActionDisplay test = new ActionDisplay(test_players);      
        frame.setSize(700, 500);
        frame.setVisible(true);
        frame.add(test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates new form AcitonDisplay
     */
    public ActionDisplay(ArrayList<Player> players) {
    	this.handler = new UDPHandler(this);
    	this.handler.start();
        initComponents();
        this.players = players;
        playerList();
    }

    private void playerList(){
        //Create list of players for each team
        //ArrayList<JLabel> red_players = new ArrayList<JLabel>();
        //ArrayList<JLabel> green_players = new ArrayList<JLabel>();

        //Create empty labels reserved for 15 members each
        for(int i = 0; i < 15; i++){
            this.red_players.add(new JLabel());
            this.green_players.add(new JLabel());
        }
        
        int red_counter = 0;
        int green_counter = 0;

        //Assigns a jLabel for each player in the game
        for(Player p : this.players){
            

            if(p.getTeam().equals("red")){
                JLabel playerLabel = this.red_players.get(red_counter);
                p.setLabel(playerLabel);
                playerLabel.setForeground(Color.RED);
                playerLabel.setText(p.getCodeName() + "     " + p.getScore());
                playerLabel.setBounds(60,(75 + (red_counter * 20)), 200,30);
                jPanel1.add(playerLabel);
    
                red_counter++;
            }
            else{
                JLabel playerLabel = this.green_players.get(green_counter);
                p.setLabel(playerLabel);
                playerLabel.setForeground(Color.GREEN);
                playerLabel.setText(p.getCodeName() + "     " + p.getScore());
                playerLabel.setBounds(410,(75 + (green_counter * 20)), 200,30);
                jPanel1.add(playerLabel);

                green_counter++;
            }
        }
    }  
    
    //Makes the set blinking label blink on the screen
    private void blinkPlayer(Player p){
        //Creates and sets new blink timer
        Timer timer = new Timer();
        this.blink_timer = timer;
        JLabel label = p.getLabel();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                if(label.getForeground() != Color.BLACK){
                    label.setForeground(Color.BLACK);
                }
                else{
                    if(p.getTeam().equals("red")){
                        label.setForeground(Color.RED);
                    }
                    else{
                        label.setForeground(Color.GREEN);
                    }
                }
            }
        }, 0, 500);
    }

    private void updateScores(Player attacker, Player target){
        JLabel attacker_label = attacker.getLabel();
        JLabel target_label = target.getLabel();

        //Updates Player Labels (if they have been set)
        if(attacker_label != null && target_label != null){
            //Error handling for Friendly Fire (in case there is faulty input in traffic generator)
            if(!attacker.getTeam().equals(target.getTeam())){
                //Determines which team the attacker is on
                if(attacker.getTeam().equals("red")){
                    //Sets score and label for attacker
                    attacker.setScore(attacker.getScore() + 10);
                    attacker_label.setText(attacker.getCodeName() + "     " + attacker.getScore());
                    redScore += 10;
                    jLabel1.setText("RED TEAM: " + redScore);
            

                    //Sets score and label for target (Does nothing if target's score is already 0)
                    if(target.getScore() > 0){
                        target.setScore(target.getScore() - 10);
                        target_label.setText(target.getCodeName() + "     " + target.getScore());
                        greenScore -= 10;
                        jLabel2.setText("GREEN TEAM: " + greenScore);
                    }
                }
                else{
                    //Sets score and label for attacker
                    attacker.setScore(attacker.getScore() + 10);
                    attacker_label.setText(attacker.getCodeName() + "     " + attacker.getScore());
                    greenScore += 10;
                    jLabel2.setText("GREEN TEAM: " + greenScore);

                    //Sets score and label for target (Does nothing if target's score is already 0)
                    if(target.getScore() > 0){
                        target.setScore(target.getScore() - 10);
                        target_label.setText(target.getCodeName() + "     " + target.getScore());
                        redScore -= 10;
                        jLabel1.setText("RED TEAM: " + redScore);
                    }
                }
                
                //Calls this method each time a new score occurs
                handleHighScore();
            }  
        }
    }

    //Searches for player with highest score, and sets blinking label to that player's label
    private void handleHighScore(){
        //Finds player with highest score and sets the blinking label to that player's label (unless that score is zero)
        Player highest_scorer = getHighestScorer();
        Player original = this.top_scorer;

        if(highest_scorer.getScore() != 0){
            this.top_scorer = highest_scorer;
            

            if(original != this.top_scorer){
                //Cancels timer and resets original label (if they exist)
                if(this.blink_timer != null){
                    this.blink_timer.cancel();
                }
                if(original != null){
                    JLabel label = original.getLabel();
                    if(original.getTeam().equals("red")){
                        label.setForeground(Color.RED);
                    }
                    else{
                        label.setForeground(Color.GREEN);
                    }
                }

                blinkPlayer(this.top_scorer);
            }
        }

        
    }

    //Handles the Game Messages that appear in the Dialog
    private void handleGameMessages(int event_counter, Player attacker, Player target){
        JLabel[] msg_labels = new JLabel[]{Msg1, Msg2, Msg3, Msg4, Msg5, Msg6, Msg7};
        //Checks if the game messages are the first 7 messages, if not then it will do a scrolling effect
        if(event_counter <= 7){
            //New event message appears (then becomes visible)
            msg_labels[event_counter - 1].setText(generateBattleString(attacker, target));
            msg_labels[event_counter - 1].setForeground(Color.white);
        }
        else{
            //Scrolling Effect
            msg_labels[0].setText(msg_labels[1].getText());
            msg_labels[1].setText(msg_labels[2].getText());
            msg_labels[2].setText(msg_labels[3].getText());
            msg_labels[3].setText(msg_labels[4].getText());
            msg_labels[4].setText(msg_labels[5].getText());
            msg_labels[5].setText(msg_labels[6].getText());

            //New event message appears
            msg_labels[6].setText(generateBattleString(attacker, target));
        }
    }

    //Generates string to be used in event message
    private String generateBattleString(Player attacker, Player target){
        Random generator = new Random();

        switch (generator.nextInt(16)){
            case 0: return target.getCodeName() + " was pummeled by " + attacker.getCodeName() + ".";
            case 1: return target.getCodeName() + " was punched by " + attacker.getCodeName() + ".";
            case 2: return target.getCodeName() + " was shot by " + attacker.getCodeName() + ".";
            case 3: return target.getCodeName() + " slipped on a banana peel left by " + attacker.getCodeName() + ".";

            case 4: return attacker.getCodeName() + " defenestrated " + target.getCodeName() + ".";
            case 5: return attacker.getCodeName() + " slapped " + target.getCodeName() + ".";
            case 6: return attacker.getCodeName() + " suplexed " + target.getCodeName() + " into the ground.";
            case 7: return attacker.getCodeName() + " pushed " + target.getCodeName() + " into a puddle of mud.";

            case 8: return target.getCodeName() + " was given a swirly by " + attacker.getCodeName() + ".";
            case 9: return target.getCodeName() + " was poked aggressively by " + attacker.getCodeName() + ".";
            case 10: return target.getCodeName() + " was hit by a spitball from " + attacker.getCodeName() + ".";
            case 11: return target.getCodeName() + " received a noogie from " + attacker.getCodeName() + ".";

            case 12: return attacker.getCodeName() + " threw a snowball at " + target.getCodeName() + ".";
            case 13: return attacker.getCodeName() + " unleashed immense power against " + target.getCodeName() + ".";
            case 14: return attacker.getCodeName() + " tackled " + target.getCodeName() + " to the ground.";
            case 15: return attacker.getCodeName() + " damaged the self confidence of " + target.getCodeName() + ".";

            default: System.out.println("Error: generateBattleString should not reach default branch."); break;
        }

        return "Error: message failed to generate. Check generateBattleString in ActionDisplay.java.";
    }

    //Returns the player with the highest score
    private Player getHighestScorer(){
        Player max_scorer = this.players.get(0);

        for(Player p: this.players){
            if(p.getScore() > max_scorer.getScore()){
                max_scorer = p;
            }
        }
        return max_scorer;
    }

    //Searches by id to assign attacker
    private Player assignAttacker(int attacker_id){
        for(Player p : this.players){
            if(p.getUserID() == attacker_id){
                return p;
            }
        }
        return new Player(-1, "N/A");
    }

    //Searches by id to assign attacker
    private Player assignTarget(int target_id){
        for(Player p : this.players){
            if(p.getUserID() == target_id){
                return p;
            }
        }
        return new Player(-1, "N/A");
    }
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Msg1 = new javax.swing.JLabel();
        Msg2 = new javax.swing.JLabel();
        Msg3 = new javax.swing.JLabel();
        Msg5 = new javax.swing.JLabel();
        Msg6 = new javax.swing.JLabel();
        Msg4 = new javax.swing.JLabel();
        Msg7 = new javax.swing.JLabel();
        
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Current Scores", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(51, 153, 255)), "XP", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 204, 51))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RED TEAM: " + redScore);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GREEN TEAM: " + greenScore);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Current Game Action", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        
//messages
        Msg1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg1.setForeground(new java.awt.Color(255, 255, 255));
        Msg1.setText("Player 1 hits Player 2");
        Msg1.setForeground(Color.BLUE);

        Msg2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg2.setForeground(new java.awt.Color(255, 255, 255));
        Msg2.setText("Player 1 hits Player 2");
        Msg2.setForeground(Color.BLUE);

        Msg3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg3.setForeground(new java.awt.Color(255, 255, 255));
        Msg3.setText("Player 1 hits Player 2");
        Msg3.setForeground(Color.BLUE);

        Msg5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg5.setForeground(new java.awt.Color(255, 255, 255));
        Msg5.setText("Player 1 hits Player 2");
        Msg5.setForeground(Color.BLUE);

        Msg6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg6.setForeground(new java.awt.Color(255, 255, 255));
        Msg6.setText("Player 1 hits Player 2");
        Msg6.setForeground(Color.BLUE);
       
        Msg4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg4.setForeground(new java.awt.Color(255, 255, 255));
        Msg4.setText("Player 1 hits Player 2");
        Msg4.setForeground(Color.BLUE);

        Msg7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Msg7.setForeground(new java.awt.Color(255, 255, 255));
        Msg7.setText("Player 1 hits Player 2");
        Msg7.setForeground(Color.BLUE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Msg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Msg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Msg3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Msg5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Msg6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Msg7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Msg4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(Msg1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Msg2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Msg3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Msg4)
                .addGap(4, 4, 4)
                .addComponent(Msg5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Msg6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Msg7)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Time Remaining:");
        jLabel3.setVisible(false);

       
       
        CombatClock clock = new CombatClock();
        clock.setForeground(new java.awt.Color(255, 255, 255));
        clock.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        clock.StartWarmup();
        clock.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clock))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>                        

    //This function is called from the backend whenever an event happens
    public void handleAction(int attacker_id, int target_id)
    {
        //Assign Target and Attacker players
        this.attacker = assignAttacker(attacker_id);
        this.target = assignTarget(target_id);
        
        //If target and attacker are not found (id of -1), then events do not occur
        if(this.attacker.getUserID() != -1 && this.target.getUserID() != -1){
            //Update Counter
            this.event_counter++;

            //Updates scores for both attacker and target
            updateScores(this.attacker, this.target);

            //Handles Game Messages (method still unfinished)
            handleGameMessages(this.event_counter, this.attacker, this.target);
        }
    }

    // Variables declaration - do not modify 
    private javax.swing.JLabel Msg1;
    private javax.swing.JLabel Msg2;
    private javax.swing.JLabel Msg3;
    private javax.swing.JLabel Msg4;
    private javax.swing.JLabel Msg5;
    private javax.swing.JLabel Msg6;
    private javax.swing.JLabel Msg7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;

    // End of variables declaration                   
}
