package kr.co.netbro.kra.rate.resource;

public interface IRegistriesConfiguration {
	/**  
    * Gets passed over to the <code>Registries</code> where it is used to  
    * configure it self. Typical content: <br>  
    * mr.putColor("active element", new RGB(0, 255, 0)); <br>  
    * mr.putImage("dialog.header", "icons/background.jpg"); <br>  
    * mr.putFont("view.headline", new FontData[] { new FontData("Tahoma", 10,  
    * SWT.BOLD) });  
    */  
   public void configure(Registries registries);
}
