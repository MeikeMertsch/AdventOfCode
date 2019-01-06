(ns christmas.chr15.day03-test
  (:require [christmas.chr15.day03 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day03real"))

(expect [2 5] (chr/walk [2 4] \v))

(expect 2 (chr/day03 ">"))
(expect 4 (chr/day03 "^>v<"))
(expect 2 (chr/day03 "^v^v^v^v^v"))
(expect 2081 (chr/day03 file))

(expect 3 (chr/day03b "^>v<"))
(expect 11 (chr/day03b "^v^v^v^v^v"))
(expect 2341 (chr/day03b file))
)