package kr.co.netbro.kra.rate.dialogs;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class PreferenceDialog extends TitleAreaDialog {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject @Preference(nodePath = "kra.config.socket")
	private IEclipsePreferences prefs;
	
	@Inject @Preference(nodePath="kra.config.socket", value="port") 
	private Integer port;
	@Inject @Preference(nodePath="kra.config.socket", value="timeout") 
	private Integer timeout;
	
	@Inject
	public PreferenceDialog(@Optional @Named(IServiceConstants.ACTIVE_SHELL) Shell parentShell) {
		super(parentShell);
	}
	
	@Inject
	public void setup() {
		
	}
	
	@Override
	protected void okPressed() {
		if(StringUtils.isNotBlank(serverPortText.getText())) {
			logger.debug("port: "+serverPortText.getText());
			prefs.remove("port");
			prefs.putInt("port", Integer.parseInt(serverPortText.getText()));
		}
		if(StringUtils.isNotBlank(timeoutText.getText())) {
			prefs.remove("timeout");
			prefs.putInt("timeout", Integer.parseInt(timeoutText.getText()));
		}
		try {
			prefs.flush();
			prefs.sync();
			super.okPressed();
		} catch (Exception e) {
			logger.error("PreferenceHandler set error", e);
			ErrorDialog.openError(
					getShell(),
					"Error",
					"Something happened while a socket is setting configration.",
					new Status(IStatus.ERROR, "kra.config.socket", e.getMessage(), e));
		}
		
	}
	
	private Text serverPortText;
	private Text timeoutText;
	
	protected Control createDialogArea(Composite parent) {
		setMessage("\uD3EC\uD2B8 \uC124\uC815 \uBC0F \uD074\uB77C\uC774\uC5B8\uD2B8\uC640\uC758 \uC5F0\uACB0 \uB300\uAE30\uC2DC\uAC04\uC744 \uC124\uC815\uD569\uB2C8\uB2E4.");
		setTitle("\uC11C\uBC84 \uC18C\uCF13 \uC124\uC815\uAD00\uB9AC");
		Composite area = (Composite) super.createDialogArea(parent);
		
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.marginRight = 5;
		gl_parent.marginLeft = 5;
		gl_parent.horizontalSpacing = 5;
		gl_parent.marginWidth = 5;
		container.setLayout(gl_parent);
		
		
		Group serverSocketGroup = new Group(container, SWT.NONE);
		GridData gd_restfulGroup = new GridData(GridData.FILL_HORIZONTAL);
		gd_restfulGroup.horizontalSpan = 3;
		serverSocketGroup.setLayoutData(gd_restfulGroup);
		serverSocketGroup.setText("\uC11C\uBC84 \uC18C\uCF13 \uC124\uC815");

		GridLayout gl_restful = new GridLayout(4, false);
		gl_restful.marginRight = 5;
		gl_restful.marginLeft = 5;
		gl_restful.horizontalSpacing = 10;
		gl_restful.marginWidth = 5;
		serverSocketGroup.setLayout(gl_restful);
		
		Label serverIPLabel = new Label(serverSocketGroup, SWT.NONE);
		serverIPLabel.setText("Server Port");
		
		serverPortText = new Text(serverSocketGroup, SWT.BORDER);
		serverPortText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverPortText.setText(String.valueOf(port));
		
		Label timeoutLabel = new Label(serverSocketGroup, SWT.NONE);
		timeoutLabel.setText("Waiting Time");
		
		timeoutText = new Text(serverSocketGroup, SWT.BORDER);
		timeoutText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		timeoutText.setText(String.valueOf(timeout));
		
		return area;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
