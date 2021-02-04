package ui;

public class Controller {

	
	public void start()
	{
		CLI cli=new CLI();
		cli.showIntro();
		
		Parser parser=new Parser();
		String command=new String();
		boolean stop;
		
		do 
		{
			command=cli.showPrompt();
			stop=parser.parse(command);
		}
		while(stop==false);
		cli.exit();
		System.exit(0);
		
	}

}
