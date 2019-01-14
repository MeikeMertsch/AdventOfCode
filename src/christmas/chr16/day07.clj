(ns christmas.chr16.day07
  (:require [clojure.string :as str]))

(def regex-hyper #"\[|\]")

(defn contains-abba? [entry]
	(->> (re-find #"(\w)(\w)\2\1" entry)
		 first
		 (#(not= (first %) (second %)))))

(defn supports-tls [line]
	(->> (cons false line)
		 (partition 2)
		 (apply map vector)
		 (#(and (every? false? (first %))
		 		(some true? (second %))))))

(defn parse-line [line]
	(->> (str/split line regex-hyper)
		 (map contains-abba?)))

(defn day07 [input]
	(->> (str/split-lines input)
		 (map parse-line)
		 (filter supports-tls)
		 count))

(defn triples [part]
	(->> (partition 3 1 part)
		 (filter #(= (first %) (last %)))
		 (filter #(not= (first %) (second %)))))

(defn divide-hyper [net]
	(->> (cons [] net)
		 (partition 2)
		 (apply map vector)))

(defn compare-ssl-candidates [[hyper outside]]
	(for [[a b c] hyper
		  [d e f] outside
		  :when (= b d f)
		  :when (= a c e)]
		  [a b c d e f]))

(defn supports-ssl? [net]
	(->> (map triples net)
		 divide-hyper
		 (map (partial apply concat))
		 compare-ssl-candidates
		 first
		 some?))

(defn parse-net [net]
	(->> (str/split-lines net)
		 (map #(str/split % regex-hyper))))

(defn day07b [input]
	(->> (parse-net input)
		 (filter supports-ssl?)
		 count))
