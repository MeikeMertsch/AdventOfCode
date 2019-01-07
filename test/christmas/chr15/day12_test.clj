(ns christmas.chr15.day12-test
  (:require [christmas.chr15.day12 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/christmas/chr15/day12real"))

(expect 191164 (chr/count-numbers file))