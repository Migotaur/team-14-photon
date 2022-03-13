package frontend;
import backend.*;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.awt.event.*;

public class PlayerEntryScreen extends javax.swing.JPanel implements KeyListener{

    //Database Connection and Player List(Global)
    PlayerSQLDatabasseConnection DB = new PlayerSQLDatabasseConnection();
    ArrayList<Player> players;
    int MAX_TEAM_PLAYERS = 14;
    int green_players;
    int red_players;

    public PlayerEntryScreen() {
        initComponents();
        addListeners();

        players = new ArrayList<Player>();
        this.addKeyListener(this);

        this.green_players = 0;
        this.red_players = 0;
    }
    
    public void keyPressed(KeyEvent e)
	{
    }

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_F5: goToPlayActionScreen();break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

    private void addListeners(){
        //Array List containing all id fields
        ArrayList<JTextField> id_fields = new ArrayList<JTextField>();
        id_fields.add(red_id_field0);
        id_fields.add(red_id_field1);
        id_fields.add(red_id_field2);
        id_fields.add(red_id_field3);
        id_fields.add(red_id_field4);
        id_fields.add(red_id_field5);
        id_fields.add(red_id_field6);
        id_fields.add(red_id_field7);
        id_fields.add(red_id_field8);
        id_fields.add(red_id_field9);
        id_fields.add(red_id_field10);
        id_fields.add(red_id_field11);
        id_fields.add(red_id_field12);
        id_fields.add(red_id_field13);
        id_fields.add(green_id_field0);
        id_fields.add(green_id_field1);
        id_fields.add(green_id_field2);
        id_fields.add(green_id_field3);
        id_fields.add(green_id_field4);
        id_fields.add(green_id_field5);
        id_fields.add(green_id_field6);
        id_fields.add(green_id_field7);
        id_fields.add(green_id_field8);
        id_fields.add(green_id_field9);
        id_fields.add(green_id_field10);
        id_fields.add(green_id_field11);
        id_fields.add(green_id_field12);
        id_fields.add(green_id_field13);

        //Array List containing all name fields
        ArrayList<JTextField> name_fields = new ArrayList<JTextField>();
        name_fields.add(red_name_field0);
        name_fields.add(red_name_field1);
        name_fields.add(red_name_field2);
        name_fields.add(red_name_field3);
        name_fields.add(red_name_field4);
        name_fields.add(red_name_field5);
        name_fields.add(red_name_field6);
        name_fields.add(red_name_field7);
        name_fields.add(red_name_field8);
        name_fields.add(red_name_field9);
        name_fields.add(red_name_field10);
        name_fields.add(red_name_field11);
        name_fields.add(red_name_field12);
        name_fields.add(red_name_field13);
        name_fields.add(green_name_field0);
        name_fields.add(green_name_field1);
        name_fields.add(green_name_field2);
        name_fields.add(green_name_field3);
        name_fields.add(green_name_field4);
        name_fields.add(green_name_field5);
        name_fields.add(green_name_field6);
        name_fields.add(green_name_field7);
        name_fields.add(green_name_field8);
        name_fields.add(green_name_field9);
        name_fields.add(green_name_field10);
        name_fields.add(green_name_field11);
        name_fields.add(green_name_field12);
        name_fields.add(green_name_field13);

        //Action Handling for ID Fields
        for(JTextField id_field : id_fields){
            id_field.addKeyListener(this);

            id_field.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                   //Checks if text is an integer, then querys database for that id
                   try{
                    int playerID = Integer.parseInt(id_field.getText());
                    int index = id_fields.indexOf(id_field);

                    JTextField name_field = name_fields.get(index);
                    String playerCodename = DB.getPlayerCodename(playerID);

                    //Checks if player is already in the player list
                    if(getPlayer(playerCodename) == null){
                        //Query Database for Player ID
                        if(DB.idInDatabase(playerID)){
                            

                            //Handles the Player Information
                            Player newPlayer = new Player(playerID, playerCodename);
                            addPlayer(newPlayer, index);

                            name_field.setText(playerCodename);
                            name_field.setFocusable(false);
                            id_field.transferFocus();
                        }
                        //If ID is not in database, go to name field
                        else{
                            label1.setText("New ID Detected. Provide a Codename for this ID, then press <Enter> to submit");
                            name_field.setFocusable(true);
                            id_field.transferFocus();
                        }
                    }
                    else{
                        id_field.setText("");
                    }

                   }catch(Exception e){
                       id_field.setText("");
                   }
                }
            });
        }

        //Action handling for Name Fields
        for(JTextField name_field : name_fields){
            //Name fields are not focused on unless its to enter a new codename
            name_field.setFocusable(false);

            name_field.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                   try{
                    String playerCodename = name_field.getText();
                    int index = name_fields.indexOf(name_field);

                    JTextField id_field = id_fields.get(index);
                    int playerID = Integer.parseInt(id_field.getText());
                    
                    //Querys if codename is already taken
                    if(!DB.codenameInDatabase(playerCodename)){
                        //Adds Player to Database
                        Player newPlayer = new Player(playerID, playerCodename);
                        DB.AddPlayerToDatabase(newPlayer);
                        addPlayer(newPlayer, index);

                        //Proceeds to next ID Field
                        label1.setText("Provide the player's ID and hit <Enter>");
                        name_field.transferFocus();
                        name_field.setFocusable(false);
                    }
                    else{
                        name_field.setText("");
                    }
                   }catch(Exception e){
                        System.exit(0);
                   }
                }
            });
        }   
    }

    private void goToPlayActionScreen(){
        //Does not advance to screen if teams are uneven
        if(this.green_players == this.red_players && !players.isEmpty()){
            javax.swing.JFrame parentFrame = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.dispose();

            javax.swing.JFrame frame = new javax.swing.JFrame();
            ActionDisplay test = new ActionDisplay(players);
        
            frame.setSize(700, 500);
            frame.setVisible(true);
            frame.add(test);
        }
        else{
            label1.setText("Unable to start game. Make sure teams are even with at least one player");
        }
    }
//Adds Player to Game and sets their team
private void addPlayer(Player p, int index){
    //If codename is not in player list, add it to player list (Prevent Duplicates)
    Player playerMatch = getPlayer(p.getCodeName());
    if(playerMatch == null){
        players.add(p);
        playerMatch = p;
    }

    //Based on index of where ID Field was, sets the team for that Player
    if(index > MAX_TEAM_PLAYERS -1){
        System.out.println("Player has been assigned to the Green Team");
        this.green_players++;
    }
    else{
        System.out.println("Player has been assigned to the Red Team");
        this.red_players++;
    }
}

//Checks if a codename is already in players
private Player getPlayer(String codeword){
    for(Player p : players){
        if(codeword.equals(p.getCodeName())){
            return p;
        }
    }
    return null;
}

// Components in Player Entry Screen (Collapsing Advised)                         
private void initComponents() {

    edit_game_label = new java.awt.Label();
    red_team_container = new java.awt.Panel();
    red_id_field0 = new javax.swing.JTextField();
    red_name_field0 = new javax.swing.JTextField();
    red_id_field1 = new javax.swing.JTextField();
    red_name_field1 = new javax.swing.JTextField();
    red_id_field2 = new javax.swing.JTextField();
    red_name_field2 = new javax.swing.JTextField();
    red_id_field3 = new javax.swing.JTextField();
    red_name_field3 = new javax.swing.JTextField();
    red_id_field4 = new javax.swing.JTextField();
    red_name_field4 = new javax.swing.JTextField();
    red_id_field5 = new javax.swing.JTextField();
    red_name_field5 = new javax.swing.JTextField();
    red_id_field6 = new javax.swing.JTextField();
    red_name_field6 = new javax.swing.JTextField();
    red_id_field7 = new javax.swing.JTextField();
    red_name_field7 = new javax.swing.JTextField();
    red_id_field8 = new javax.swing.JTextField();
    red_id_field9 = new javax.swing.JTextField();
    red_name_field9 = new javax.swing.JTextField();
    red_name_field10 = new javax.swing.JTextField();
    red_name_field11 = new javax.swing.JTextField();
    red_id_field11 = new javax.swing.JTextField();
    red_id_field12 = new javax.swing.JTextField();
    red_name_field13 = new javax.swing.JTextField();
    red_id_field10 = new javax.swing.JTextField();
    red_id_field13 = new javax.swing.JTextField();
    red_name_field12 = new javax.swing.JTextField();
    red_name_field8 = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    green_team_container = new java.awt.Panel();
    green_id_field0 = new javax.swing.JTextField();
    green_name_field0 = new javax.swing.JTextField();
    green_id_field1 = new javax.swing.JTextField();
    green_name_field1 = new javax.swing.JTextField();
    green_id_field2 = new javax.swing.JTextField();
    green_name_field2 = new javax.swing.JTextField();
    green_id_field3 = new javax.swing.JTextField();
    green_name_field3 = new javax.swing.JTextField();
    green_id_field4 = new javax.swing.JTextField();
    green_name_field4 = new javax.swing.JTextField();
    green_id_field5 = new javax.swing.JTextField();
    green_name_field5 = new javax.swing.JTextField();
    green_id_field6 = new javax.swing.JTextField();
    green_name_field6 = new javax.swing.JTextField();
    green_id_field7 = new javax.swing.JTextField();
    green_name_field7 = new javax.swing.JTextField();
    green_id_field8 = new javax.swing.JTextField();
    green_name_field8 = new javax.swing.JTextField();
    green_id_field9 = new javax.swing.JTextField();
    green_name_field9 = new javax.swing.JTextField();
    green_id_field10 = new javax.swing.JTextField();
    green_name_field10 = new javax.swing.JTextField();
    green_id_field11 = new javax.swing.JTextField();
    green_name_field11 = new javax.swing.JTextField();
    green_id_field12 = new javax.swing.JTextField();
    green_name_field12 = new javax.swing.JTextField();
    green_id_field13 = new javax.swing.JTextField();
    green_name_field13 = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    game_model_label = new java.awt.Label();
    label1 = new java.awt.Label();
    f1_label = new javax.swing.JLabel();
    f1_label1 = new javax.swing.JLabel();
    f1_label2 = new javax.swing.JLabel();
    f1_label3 = new javax.swing.JLabel();
    f1_label5 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();

    setBackground(new java.awt.Color(0, 0, 0));

    edit_game_label.setAlignment(java.awt.Label.CENTER);
    edit_game_label.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
    edit_game_label.setForeground(new java.awt.Color(51, 255, 255));
    edit_game_label.setText("Edit Current Game");

    red_team_container.setBackground(new java.awt.Color(153, 0, 0));
    red_team_container.setPreferredSize(new java.awt.Dimension(200, 400));

    red_name_field13.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            //red_name_field13ActionPerformed(evt);
        }
    });

    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel4.setText("RED TEAM");

    javax.swing.GroupLayout red_team_containerLayout = new javax.swing.GroupLayout(red_team_container);
    red_team_container.setLayout(red_team_containerLayout);
    red_team_containerLayout.setHorizontalGroup(
        red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(red_team_containerLayout.createSequentialGroup()
            .addGap(41, 41, 41)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(red_team_containerLayout.createSequentialGroup()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, red_team_containerLayout.createSequentialGroup()
                    .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addComponent(red_id_field3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(red_name_field3))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addComponent(red_id_field4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(red_name_field4))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addComponent(red_id_field5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(red_name_field5))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addComponent(red_id_field6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(red_name_field6))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addComponent(red_id_field7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(red_name_field7))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(red_id_field9)
                                .addComponent(red_id_field8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red_name_field9)
                                .addComponent(red_name_field8)))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(red_id_field11, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red_id_field10)
                                .addComponent(red_id_field12, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red_id_field13, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red_name_field13)
                                .addComponent(red_name_field11)
                                .addComponent(red_name_field12)
                                .addComponent(red_name_field10)))
                        .addGroup(red_team_containerLayout.createSequentialGroup()
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(red_id_field2)
                                .addComponent(red_id_field0)
                                .addComponent(red_id_field1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red_name_field2)
                                .addComponent(red_name_field1)
                                .addComponent(red_name_field0))))
                    .addGap(14, 14, 14))))
    );
    red_team_containerLayout.setVerticalGroup(
        red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(red_team_containerLayout.createSequentialGroup()
            .addComponent(jLabel4)
            .addGap(7, 7, 7)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field0)
                .addComponent(red_name_field0))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field1)
                .addComponent(red_name_field1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field2)
                .addComponent(red_name_field2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field3)
                .addComponent(red_name_field3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field4)
                .addComponent(red_name_field4))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field5)
                .addComponent(red_name_field5))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field6)
                .addComponent(red_name_field6))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field7)
                .addComponent(red_name_field7))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field8)
                .addComponent(red_name_field8))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field9)
                .addComponent(red_name_field9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field10)
                .addComponent(red_name_field10))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field11)
                .addComponent(red_name_field11))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field12)
                .addComponent(red_name_field12))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(red_id_field13)
                .addComponent(red_name_field13))
            .addGap(39, 39, 39))
    );

    green_team_container.setBackground(new java.awt.Color(51, 102, 0));
    green_team_container.setPreferredSize(new java.awt.Dimension(200, 400));

    green_name_field13.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
           // green_name_field13ActionPerformed(evt);
        }
    });

    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel5.setText("GREEN TEAM");

    javax.swing.GroupLayout green_team_containerLayout = new javax.swing.GroupLayout(green_team_container);
    green_team_container.setLayout(green_team_containerLayout);
    green_team_containerLayout.setHorizontalGroup(
        green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, green_team_containerLayout.createSequentialGroup()
            .addGap(41, 41, 41)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addComponent(green_id_field3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(green_name_field3))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addComponent(green_id_field4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(green_name_field4))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addComponent(green_id_field5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(green_name_field5))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addComponent(green_id_field6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(green_name_field6))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addComponent(green_id_field7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(green_name_field7))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(green_id_field9)
                        .addComponent(green_id_field8))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(green_name_field9)
                        .addComponent(green_name_field8)))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(green_id_field2)
                        .addComponent(green_id_field0)
                        .addComponent(green_id_field1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(green_name_field2)
                        .addComponent(green_name_field1)
                        .addComponent(green_name_field0)))
                .addGroup(green_team_containerLayout.createSequentialGroup()
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(green_id_field11, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(green_id_field10)
                        .addComponent(green_id_field12, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(green_id_field13, javax.swing.GroupLayout.Alignment.LEADING))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(green_name_field13)
                        .addComponent(green_name_field11)
                        .addComponent(green_name_field12)
                        .addComponent(green_name_field10))))
            .addGap(14, 14, 14))
        .addGroup(green_team_containerLayout.createSequentialGroup()
            .addGap(80, 80, 80)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    green_team_containerLayout.setVerticalGroup(
        green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(green_team_containerLayout.createSequentialGroup()
            .addComponent(jLabel5)
            .addGap(7, 7, 7)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field0)
                .addComponent(green_name_field0))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field1)
                .addComponent(green_name_field1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field2)
                .addComponent(green_name_field2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field3)
                .addComponent(green_name_field3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field4)
                .addComponent(green_name_field4))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field5)
                .addComponent(green_name_field5))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field6)
                .addComponent(green_name_field6))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field7)
                .addComponent(green_name_field7))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field8)
                .addComponent(green_name_field8))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field9)
                .addComponent(green_name_field9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field10)
                .addComponent(green_name_field10))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field11)
                .addComponent(green_name_field11))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field12)
                .addComponent(green_name_field12))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(green_id_field13)
                .addComponent(green_name_field13))
            .addGap(37, 37, 37))
    );

    game_model_label.setAlignment(java.awt.Label.CENTER);
    game_model_label.setBackground(new java.awt.Color(153, 153, 153));
    game_model_label.setForeground(new java.awt.Color(255, 255, 255));
    game_model_label.setText("Game Mode: Standard Public Mode");

    label1.setAlignment(java.awt.Label.CENTER);
    label1.setBackground(new java.awt.Color(204, 204, 204));
    label1.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
    label1.setText("Provide the player's ID and hit <Enter>");

    f1_label.setBackground(new java.awt.Color(0, 0, 0));
    f1_label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    f1_label.setForeground(new java.awt.Color(0, 153, 51));
    f1_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    f1_label.setText("<html>F3<br>Start <br> Game");
    f1_label.setToolTipText("");
    f1_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    f1_label1.setBackground(new java.awt.Color(0, 0, 0));
    f1_label1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    f1_label1.setForeground(new java.awt.Color(0, 153, 51));
    f1_label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    f1_label1.setText("<html>F1<br>Edit <br> Game");
    f1_label1.setToolTipText("");
    f1_label1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    f1_label2.setBackground(new java.awt.Color(0, 0, 0));
    f1_label2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    f1_label2.setForeground(new java.awt.Color(0, 153, 51));
    f1_label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    f1_label2.setText("<html>F2<br>Game <br> Parameters");
    f1_label2.setToolTipText("");
    f1_label2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    f1_label3.setBackground(new java.awt.Color(0, 0, 0));
    f1_label3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    f1_label3.setForeground(new java.awt.Color(0, 153, 51));
    f1_label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    f1_label3.setText("<html>F5<br>Game <br>Preview");
    f1_label3.setToolTipText("");
    f1_label3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    f1_label5.setBackground(new java.awt.Color(0, 0, 0));
    f1_label5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    f1_label5.setForeground(new java.awt.Color(0, 153, 51));
    f1_label5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    f1_label5.setText("<html>F12<br>Clear <br>Game");
    f1_label5.setToolTipText("");
    f1_label5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 153, 51));
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("<html>F10<br>Flick <br> Sync");
    jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 153, 51));
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("F7");
    jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 153, 51));
    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("<html>F8<br>View <br> Game");
    jLabel3.setToolTipText("");
    jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(edit_game_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(71, 71, 71)
            .addComponent(red_team_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(green_team_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(45, 45, 45))
        .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(f1_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(f1_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(f1_label, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(77, 77, 77)
                    .addComponent(f1_label3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(81, 81, 81)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(game_model_label, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(136, 136, 136)))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(38, 38, 38)
            .addComponent(f1_label5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(edit_game_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(red_team_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(green_team_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(1, 1, 1)
            .addComponent(game_model_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(f1_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(f1_label5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(f1_label, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_label3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(f1_label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
}// </editor-fold>

    // Variables declaration - do not modify                     
    private java.awt.Label edit_game_label;
    private javax.swing.JLabel f1_label;
    private javax.swing.JLabel f1_label1;
    private javax.swing.JLabel f1_label2;
    private javax.swing.JLabel f1_label3;
    private javax.swing.JLabel f1_label5;
    private java.awt.Label game_model_label;
    private JTextField green_id_field0;
    private javax.swing.JTextField green_id_field1;
    private javax.swing.JTextField green_id_field10;
    private javax.swing.JTextField green_id_field11;
    private javax.swing.JTextField green_id_field12;
    private javax.swing.JTextField green_id_field13;
    private javax.swing.JTextField green_id_field2;
    private javax.swing.JTextField green_id_field3;
    private javax.swing.JTextField green_id_field4;
    private javax.swing.JTextField green_id_field5;
    private javax.swing.JTextField green_id_field6;
    private javax.swing.JTextField green_id_field7;
    private javax.swing.JTextField green_id_field8;
    private javax.swing.JTextField green_id_field9;
    private javax.swing.JTextField green_name_field0;
    private javax.swing.JTextField green_name_field1;
    private javax.swing.JTextField green_name_field10;
    private javax.swing.JTextField green_name_field11;
    private javax.swing.JTextField green_name_field12;
    private javax.swing.JTextField green_name_field13;
    private javax.swing.JTextField green_name_field2;
    private javax.swing.JTextField green_name_field3;
    private javax.swing.JTextField green_name_field4;
    private javax.swing.JTextField green_name_field5;
    private javax.swing.JTextField green_name_field6;
    private javax.swing.JTextField green_name_field7;
    private javax.swing.JTextField green_name_field8;
    private javax.swing.JTextField green_name_field9;
    private java.awt.Panel green_team_container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private java.awt.Label label1;
    private javax.swing.JTextField red_id_field0;
    private javax.swing.JTextField red_id_field1;
    private javax.swing.JTextField red_id_field10;
    private javax.swing.JTextField red_id_field11;
    private javax.swing.JTextField red_id_field12;
    private javax.swing.JTextField red_id_field13;
    private javax.swing.JTextField red_id_field2;
    private javax.swing.JTextField red_id_field3;
    private javax.swing.JTextField red_id_field4;
    private javax.swing.JTextField red_id_field5;
    private javax.swing.JTextField red_id_field6;
    private javax.swing.JTextField red_id_field7;
    private javax.swing.JTextField red_id_field8;
    private javax.swing.JTextField red_id_field9;
    private javax.swing.JTextField red_name_field0;
    private javax.swing.JTextField red_name_field1;
    private javax.swing.JTextField red_name_field10;
    private javax.swing.JTextField red_name_field11;
    private javax.swing.JTextField red_name_field12;
    private javax.swing.JTextField red_name_field13;
    private javax.swing.JTextField red_name_field2;
    private javax.swing.JTextField red_name_field3;
    private javax.swing.JTextField red_name_field4;
    private javax.swing.JTextField red_name_field5;
    private javax.swing.JTextField red_name_field6;
    private javax.swing.JTextField red_name_field7;
    private javax.swing.JTextField red_name_field8;
    private javax.swing.JTextField red_name_field9;
    private java.awt.Panel red_team_container;
    // End of variables declaration                   
}
