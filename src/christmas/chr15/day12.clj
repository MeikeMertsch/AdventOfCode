(ns christmas.chr15.day12
  (:require [clojure.string :as str]
  			[christmas.tools :as t]
  			[cheshire.core :refer :all]))

(def regex #"\{[^\{^\}]*\}")
(def split-regex #"\[[^\[^\]]*\]")

(defn add-numbers [input]
	(->> (re-seq #"-*\d+" input)
		 (map t/parse-int)
		 (apply +)))

(defn get-rid-of-groups [string]
	(->> (iterate #(str/replace % split-regex "") string)
		 (drop-while #(re-find #"\[" %))
		 first))

(defn value [string]
	(->> (get-rid-of-groups string)
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
