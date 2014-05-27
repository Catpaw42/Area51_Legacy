public interface IWeightController {
		double readWeight();
		double tareWeight();
		void zeroWeight();
		void displayText(String text);
		void displayWeight();
		void setActiveUser(int ID);
		void getActiveUser(int ID);
		void close();
}
