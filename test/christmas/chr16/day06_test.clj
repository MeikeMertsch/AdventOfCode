(ns christmas.chr16.day06-test
  (:require [christmas.chr16.day06 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day06real"))