(ns christmas.chr15.day09-test
  (:require [christmas.chr15.day09 :as chr]
  		  	[expectations :refer :all]
  		  	[christmas.tools :as t]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day09real"))
(def t-file "London to Dublin = 464\nLondon to Belfast = 518\nDublin to Belfast = 141")

(expect {#{"Belfast" "London"} 518, #{"London" "Dublin"} 464, #{"Belfast" "Dublin"} 141} (chr/parse-file t-file))
(expect ["London" "Dublin" "Belfast"] (chr/all-dest t-file))

(expect #{[0 1 2][0 2 1][1 0 2][1 2 0][2 0 1][2 1 0]} (into #{} (chr/combos [0 1 2])))

(expect [605 982] (chr/day09 t-file))
(expect [251 898] (chr/day09 file))
)