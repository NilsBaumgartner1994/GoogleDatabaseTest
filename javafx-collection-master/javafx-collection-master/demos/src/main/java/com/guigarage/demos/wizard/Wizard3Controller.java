package com.guigarage.demos.wizard;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

/**
 * This is a view controller for one of the steps in the wizard. All buttons of the action-bar that
 * is shown on each view of the wizard are defined in the AbstractWizardController class. The definition of the
 * actions that are registered to these buttons can be found in the {@link WizardDemo} class.
 *
 * As you can see in the wizard3.fxml file a hyperlink is defined in the view. This link should print
 * the some help on the console. To do so a action is defined in the {@link WizardDemo} class. This action
 * is defined by the unique id "help". By injecting the Hyperlink instance in the controller with the use of
 * the @FXML annotation the hyperlink node can be bound to the action by using the {@link io.datafx.controller.flow.action.ActionTrigger} annotation.
 * So once the hyperlink will be clicked the action that is registered with the "help" id will be called.
 */
@FXMLController(value="wizard3.fxml", title = "Wizard: Step 3")
public class Wizard3Controller extends AbstractWizardController {

    @FXML
    @ActionTrigger("help")
    private Hyperlink helpLink;
}
