package kr.co.netbro.kra.rate.dialogs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PasswordDialog extends Dialog {
	
	private String passwd = "";
	private Text txtPassword;
	
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	protected PasswordDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout(2, false);
        layout.marginRight = 5;
        layout.marginLeft = 10;
        container.setLayout(layout);
        
        Label lblPassword = new Label(container, SWT.NONE);
        GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel.horizontalIndent = 1;
        lblPassword.setLayoutData(gd_lblNewLabel);
        lblPassword.setText("Password:");

        txtPassword = new Text(container, SWT.BORDER| SWT.PASSWORD);
        txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtPassword.setText(passwd);
        txtPassword.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                        Text textWidget = (Text) e.getSource();
                        String passwordText = textWidget.getText();
                        passwd = passwordText;
                }
        });
        
		return container;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\uAD00\uB9AC\uC790\20\uBE44\uBC00\uBC88\uD638"); //관리자 비밀번호
	}

	@Override
	protected Point getInitialSize() {
		return new Point(250, 110);
	}

	@Override
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL);
		setBlockOnOpen(false);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "\uD655\20\uC778", true); //확 인
		createButton(parent, IDialogConstants.CANCEL_ID, "\uCDE8\20\uC18C", false); //취 소
	}

	@Override
	protected void okPressed() {
		/*
		 * 비밀번호 valid check 후 맞지 않다면 input 데이타를 지움
		 */
		if(StringUtils.isNotBlank(getPasswd())) {
			super.okPressed();
		}
	}
}
