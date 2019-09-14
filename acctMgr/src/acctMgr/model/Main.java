package acctMgr.model;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import acctMgr.controller.AccountSelectionController;

/**
 * applicaton entry point. Command-line file selection optional.
 * @author Jon Pierre
 * @version 1.0
 */
public class Main {
	static final String testFileDir = "accountFiles";

	public static void main(String[] args) {
		String fileName = testFileDir + "\\account_list.txt";
		
		if (args.length > 0 &&  args[0] != null) {
			fileName = args[0];
		}
		else {
			try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
			catch (Exception e) {}

			JFileChooser fileChooser = new JFileChooser(new File(testFileDir));
			fileChooser.setDialogTitle("Open Account File");
			fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
			if (fileChooser.showOpenDialog(null)
					== JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				fileName = file.getAbsolutePath();
			}
		}

		new AccountSelectionController(fileName);

	}
}
