/**
 *Class for Player Entry Screen
 * 
 */
public class PlayerEntryScreen extends javax.swing.JPanel {

    /**
     * Creates new form PlayerEntryScreen
     */
    public PlayerEntryScreen() {
        initComponents();
    }
                         
    private void initComponents() {

        edit_game_label = new java.awt.Label();
        red_team_container = new java.awt.Panel();
        green_team_container = new java.awt.Panel();

        setBackground(new java.awt.Color(0, 0, 0));

        edit_game_label.setAlignment(java.awt.Label.CENTER);
        edit_game_label.setForeground(new java.awt.Color(51, 255, 255));
        edit_game_label.setText("Edit Current Game");

        red_team_container.setBackground(new java.awt.Color(153, 0, 0));
        red_team_container.setMinimumSize(new java.awt.Dimension(200, 300));

        javax.swing.GroupLayout red_team_containerLayout = new javax.swing.GroupLayout(red_team_container);
        red_team_container.setLayout(red_team_containerLayout);
        red_team_containerLayout.setHorizontalGroup(
            red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        red_team_containerLayout.setVerticalGroup(
            red_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        green_team_container.setBackground(new java.awt.Color(51, 102, 0));
        green_team_container.setMinimumSize(new java.awt.Dimension(200, 300));
        green_team_container.setPreferredSize(new java.awt.Dimension(200, 300));

        javax.swing.GroupLayout green_team_containerLayout = new javax.swing.GroupLayout(green_team_container);
        green_team_container.setLayout(green_team_containerLayout);
        green_team_containerLayout.setHorizontalGroup(
            green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        green_team_containerLayout.setVerticalGroup(
            green_team_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(edit_game_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(red_team_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(green_team_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(113, 113, 113))
        );
    }// </editor-fold>                        


    // Variables declaration                    
    private java.awt.Label edit_game_label;
    private java.awt.Panel green_team_container;
    private java.awt.Panel panel5;
    private java.awt.Panel red_team_container;
    // End of variables declaration                   
}
