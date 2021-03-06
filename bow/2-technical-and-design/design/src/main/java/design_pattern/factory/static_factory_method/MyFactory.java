//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package design_pattern.factory.static_factory_method;


class Kitchen {

}

class Tv {

}

class DinningRoom {

    public DinningRoom(Tv tv) {

    }
}

class House {
    public House(String name,
                 Kitchen Kitch,
                 DinningRoom DinningRoom) {
    }

    private House() {

    }

    public static House buildHouse(String owner, boolean expensive) {
        if (expensive == true) {
            Tv tv = new Tv();
            DinningRoom dinningRoom = new DinningRoom(tv);
            Kitchen kitchen = new Kitchen();
            return new House(owner, kitchen, dinningRoom);
        }
        return null;
    }
}

class Senaria {
    public static void main(String[] args) {
        boolean expensive = true;
        House h = House.buildHouse("Brue", expensive);
    }
}
