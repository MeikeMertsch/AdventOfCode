(ns christmas.chr15.day12-test
  (:require [christmas.chr15.day12 :as chr]
  			[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day12real"))
(def example-1 "{\"a\":{\"b\":4},\"c\":-1}")
(def example-2 "{\"a\":4,\"c\":-1}")
(def example-3 "3")

(def test-string "{\"a\":181,\"b\":[\"orange\",-40,\"red\",\"orange\",\"yellow\",31,60,71,\"yellow\"]}")
(def test-string-2 "{\"e\":-2,\"a\":\"blue\",\"d\":\"green\",\"j\":185,\"c\":\"yellow\",\"h\":[\"orange\",\"violet\",-21],\"b\":388,\"g\":115,\"f\":-41,\"i\":\"blue\"}")

(expect 191164 (chr/add-numbers file))

(expect example-2 (chr/tick example-1))
(expect example-3 (chr/tick example-2))

(expect 303 (chr/value test-string))
(expect 624 (chr/value test-string-2))

(expect 3 (chr/day12 example-1))
(expect 87842 (chr/day12 file))

(expect 6 (chr/day12 "[1,2,3]"))
(expect 4 (chr/day12 "[1,{\"c\":\"red\",\"b\":2},3]"))
(expect 0 (chr/day12 "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"))
(expect 0 (chr/day12 "{\"d\":\"[1,2,3]\",\"e\":\"red\",\"g\":[3,4],\"f\":5}"))
(expect 20 (chr/day12 "{\"d\":\"[1,2,3]\",\"e\":5,\"g\":[\"red\",4],\"f\":5}"))
(expect 18 (chr/day12 "{\"d\":\"[1,[red],3]\",\"e\":5,\"g\":[\"red\",4],\"f\":5}"))
(expect 15 (chr/day12 "{\"d\":\"[1,[red],red]\",\"e\":5,\"g\":[\"red\",4],\"f\":5}"))
(expect 6 (chr/day12 "[1,\"red\",5]"))
)