(ns christmas.chr15.day16-test
  (:require [christmas.chr15.day16 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day16real"))

(expect 213 (chr/day16 file))
(expect 323 (chr/day16b file))
)