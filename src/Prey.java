/**
 * <h1> Representation of a Consumer that consumes Sources </h1>
 */
public class Prey extends Consumer{
        public Prey() {
            Type = "Prey";
            maxEnergy = 1000;
            currentEnergy = maxEnergy;
            lifeSpan = 20;
            brain = new RandomBrain();
        }

        @Override
        protected boolean isFood(Being other) {
            if (other.Type.equals("Source"))
                return true;
            else
                return false;
        }

}
