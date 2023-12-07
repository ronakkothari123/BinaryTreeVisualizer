package Components;

import javax.swing.*;

public class ContextMenu extends JPopupMenu {
    public ContextMenu(String[] elements){
        for(int i = 0; i < elements.length; i++){
            add(new JMenuItem(elements[i]));
        }
    }
}
