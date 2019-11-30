(ns christmas.chr17.day05-test
  (:require [christmas.chr17.day05 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr17/day05real"))