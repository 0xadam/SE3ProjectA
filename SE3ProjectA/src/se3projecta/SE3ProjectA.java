/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import se3projecta.Persistance.ImportException;

/**
 *
 * @author Tobias
 */
public class SE3ProjectA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        //</editor-fold>
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Repository repository = null;
                ImportException ie = null;
                try {
                    repository = new Repository();
                } catch (ImportException e) {
                    //TODO deal with errors.
                    ie = e;
                }
                if (repository != null) {
                    repository.testDump();
                }
                new se3projecta.GUI.GUI(repository, ie).setVisible(true);
            }
        });
    }
}
