(ns christmas.chr15.day12
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn count-numbers [input]
	(->> (re-seq #"-*\d+" input)
		 (map t/parse-int)
		 (apply +)))
