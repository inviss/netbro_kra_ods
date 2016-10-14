package kr.co.netbro.kra.rate.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import kr.co.netbro.kra.model.FinalInfo;
import kr.co.netbro.kra.rate.parts.FinalSceneViewer;

public class FinalSceneDialog extends Dialog {
	private Shell shell;
	private FinalInfo finalInfo;
	
	public FinalSceneDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}
	
	public FinalSceneDialog(Shell parentShell, FinalInfo finalInfo) {
		super(parentShell);
		this.finalInfo = finalInfo;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		FinalSceneViewer viewer = new FinalSceneViewer(container);
		viewer.setData(finalInfo);
		return container;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("확정데이타 표출");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(960, 540);
	}

	@Override
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL);
		setBlockOnOpen(false);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "화면표출", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "취 소", false);
	}

	@Override
	protected void okPressed() {
		PasswordDialog passwd = new PasswordDialog(shell);
		/*
		 * 관리자 승인을 받았다면 '확정정보'를 모니터에 표시할 수 있도록 해야함.
		 * 확정정보를 json file 로 생성
		 */
		if(passwd.open() == Window.OK) {
			// get 확정정보
			// crate json file
			System.out.println(passwd.getPasswd());
			super.okPressed();
		}
	}
}
