(ns christmas.chr15.day01-test
  (:require [christmas.chr15.day01 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/christmas/chr15/day01real"))

(expect 232 (chr/day01 file))
(expect 1783 (chr/day01b file))