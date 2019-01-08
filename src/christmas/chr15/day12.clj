(ns christmas.chr15.day12
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(def regex #"\{[^\{^\}]*\}")
(def split-regex #"\[[^\{^\}]*\]")

(defn add-numbers [input]
	(->> (re-seq #"-*\d+" input)
		 (map t/parse-int)
		 (apply +)))

(defn value [string]
	(println string (add-numbers string) (apply str (str/split string split-regex)))
	(->> (str/split string split-regex)
		 (apply str)
		 (#(if (re-find #"red" %)
				0 
				(add-numbers string)))))

(defn tick [string]
	(->> (re-find regex string)
		value
		str
		(str/replace-first string regex)))

(defn day12 [string]
	(->> (iterate tick string)
		 (drop-while #(str/includes? % "{"))
		 first
		 add-numbers))