package mesquite.examples.ExFileExporter;/*~~  */import java.util.*;import mesquite.lib.*;import mesquite.lib.characters.*;import mesquite.lib.duties.*;/* ============  an example file exporter ============*/public class ExFileExporter extends FileInterpreterI {/*.................................................................................................................*/	public boolean startJob(String arguments, Object condition, boolean hiredByName) { 		return true;    	 }  	 /*.................................................................................................................*/	public String preferredDataFileExtension() { 		return "";   	 }/*.................................................................................................................*/	public boolean canExportEver() {  		 return true;	}	/*.................................................................................................................*/	public boolean canExportData(Class data) {  		 return false;	}	/*.................................................................................................................*/	public boolean canExportProject(MesquiteProject project) {  		 return true;	}	/*.................................................................................................................*/	public boolean canImport() {  		 return false;	}/*.................................................................................................................*/	public void readFile(MesquiteProject mf, MesquiteFile file, String arguments) {	}	/* ============================  exporting ============================*/	/*.................................................................................................................*/	public boolean exportFile(MesquiteFile file, String arguments) { //if file is null, consider whole project open to export		Taxa taxa =  (Taxa)getProject().chooseTaxa(containerOfModule(), "Select taxa to be exported");				int numTaxa = taxa.getNumTaxa();		StringBuffer outputBuffer = new StringBuffer(numTaxa*(20 + numTaxa));				for (int it = 0; it<numTaxa; it++){				outputBuffer.append(ParseUtil.tokenize(taxa.getTaxonName(it)) + "\t");				outputBuffer.append(getLineEnding()+getLineEnding());		}				saveExportedFile(outputBuffer.toString(), arguments, "");	}	/*.................................................................................................................*/    	 public String getName() {		return "Example File Exporter";   	 }	/*.................................................................................................................*/   	  	/** returns an explanation of what the module does.*/ 	public String getExplanation() { 		return "Illustrates a file exporter." ;   	 }	/*.................................................................................................................*/   	    	 }	