package kr.co.netbro.kra.rate.dialogs;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class PreferenceDialog extends TitleAreaDialog {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Inject @Preference(nodePath="kra.config.socket", value="port") 
	private Integer port;
	@Inject @Preference(nodePath="kra.config.socket", value="timeout") 
	private Integer timeout;
	@Inject @Preference(nodePath="kra.config.socket", value="watcherDir") 
	private String watcherDir;
	
	private Text watcherDirTxt;
	
	@Inject
	public PreferenceDialog(@Optional @Named(IServiceConstants.ACTIVE_SHELL) Shell parentShell) {
		super(parentShell);
	}
	
	@Inject
	public void setup() {
		if(port == null || port == 0) {
			port = 8000;
		}
		if(timeout == null || timeout == 0) {
			timeout = 25000;
		}
		if(StringUtils.isBlank(watcherDir)) {
			watcherDir = "";
		}
	}
	
	public Integer getPort() {
		return port;
	}
	
	public Integer getTimeout() {
		return timeout;
	}
	
	public String getWatcherDir() {
		return watcherDir;
	}
	
	@Override
	protected void okPressed() {
		if(StringUtils.isNotBlank(serverPortText.getText())) {
			logger.debug("port: "+serverPortText.getText());
			this.port = Integer.parseInt(serverPortText.getText());
		}
		if(StringUtils.isNotBlank(timeoutText.getText())) {
			this.timeout = Integer.parseInt(timeoutText.getText());
		}
		if(StringUtils.isNotBlank(watcherDirTxt.getText())) {
			watcherDir = watcherDirTxt.getText();
		}
		super.okPressed();
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
		
		Label watcherFolderLabel = new Label(serverSocketGroup, SWT.NONE);
		watcherFolderLabel.setText("Watcher Folder");
		
		watcherDirTxt = new Text(serverSocketGroup, SWT.SINGLE | SWT.BORDER);
		watcherDirTxt.setText(watcherDir);
		GridData watcherDirGrid = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		watcherDirTxt.setLayoutData(watcherDirGrid);
		
		Button watcherDirButton = new Button(serverSocketGroup, SWT.PUSH);
		watcherDirButton.setText("Browse...");
		watcherDirButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DirectoryDialog dlg = new DirectoryDialog(serverSocketGroup.getShell());
				dlg.setFilterPath(watcherDirTxt.getText());
				dlg.setText("Watcher Folder \uC120\uD0DD"); // Watcher Folder 선택
				dlg.setMessage("\uD3F4\uB354\uB97C \uC120\uD0DD\uD558\uC138\uC694"); // 폴더를 선택하세요
				String dir = dlg.open();
				if (dir != null) {
					watcherDirTxt.setText(dir);
				}
			}
		});
		
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
