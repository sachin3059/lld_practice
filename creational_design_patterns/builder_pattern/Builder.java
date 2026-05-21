package creational_design_patterns.builder_pattern;

class Pizza{
    private String size;            // required
    private boolean cheese;         // optional
    private boolean pepperoni;      // optional
    private boolean mushrooms;      // optional
    private boolean olives;         // optional


    // private constructor - only builder can create pizza
    private Pizza(Builder builder){
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
        this.olives = builder.olives;
    }


    public static class Builder {
        private String size;
        private boolean cheese;
        private boolean pepperoni;
        private boolean mushrooms;
        private boolean olives;


        public Builder(String size){
            this.size = size;
        }

        public Builder addCheese(){
            this.cheese = true;
            return this;
        }

        public Builder addPepperoni(){
            this.pepperoni = true;
            return this;
        }

        public Builder addMushrooms(){
            this.mushrooms = true;
            return this;
        }

        public Builder addOlives(){
            this.olives = true;
            return this;
        }


        public Pizza build(){
            return new Pizza(this);
        }
    }
}


/*
usage:
Pizza pizza = new Pizza.Builder("LARGE").addCheese().addPepperoni().addOlives().build();
 */


