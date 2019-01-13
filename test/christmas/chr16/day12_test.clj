(ns christmas.chr16.day12-test
  (:require [christmas.chr16.day12 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day12real"))