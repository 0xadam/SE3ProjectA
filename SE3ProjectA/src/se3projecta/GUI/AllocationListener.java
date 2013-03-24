package se3projecta.GUI;

import java.util.EventListener;

/**
 * A listener that listens for changes in an Allocation and makes required 
 * changes.
 * 
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public abstract class AllocationListener implements EventListener {
    
    /**
     * Should be called when Allocation is changed.
     */
    void changed() {
    }
;
}
