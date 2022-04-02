package frontend;
import javax.swing.JFrame;
import backend.UDPHandler;
import java.util.ArrayList;
import backend.Player;
import javax.swing.JLabel;
import java.awt.Color;

public class ActionDisplay extends javax.swing.JPanel{

    ArrayList<Player> players;
    ArrayList<JLabel> red_players = new ArrayList<JLabel>();
    ArrayList<JLabel> green_players = new ArrayList<JLabel>();
    
    Player attacker = null;
    Player target = null;
    UDPHandler handler;
    
    int redScore = 0;
    int greenScore = 0;
    
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
                
                
            }  
        }
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
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
        
        //Updates scores for both attacker and target
        updateScores(this.attacker, this.target);

        //Debug Message
        System.out.println(String.format("%s hit %s", this.attacker.getCodeName(), this.target.getCodeName()));
        
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration                   
}
