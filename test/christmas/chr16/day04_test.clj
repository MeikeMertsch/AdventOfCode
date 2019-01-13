(ns christmas.chr16.day04-test
  (:require [christmas.chr16.day04 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day04real"))