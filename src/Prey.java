
public class Prey extends Consumer{
        public Prey() {
            Type = "Prey";
            maxEnergy = 1000;
            currentEnergy = maxEnergy;
            lifeSpan = 30;
        }

        @Override
        protected boolean isFood(Being other) {
            if (other.Type.equals("Source"))
                return true;
            else
                return false;
        }

}
