package acctMgr.model;

/**
 * Exception for incorrect file format for Account file.
 * @author Jon pierre
 * @version 1.0
 */
public class IllegalFileFormatException extends IllegalArgumentException {
	private static final long serialVersionUID = -5165408124102268895L;
	String fileName = "Unknown file.";
	int lineNumber = 0;
	String msg = "Illegal file format.";
	
	public String getFileName() {return fileName;};
	public int getLine() {return lineNumber;};
	public String getMsg() {return msg;};
	
	IllegalFileFormatException(String fileName, int lineNumber, String msg) {
		super(msg);
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.msg = msg;  
	}
	
	/**
	 * @return String of user message
	 */
	public String toString() {
		return "Error. " + msg + ", " + "file name: " + "'" + fileName +  "'" + ", " + 
				"line " + lineNumber;
	}
}
