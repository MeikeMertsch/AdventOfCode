(ns christmas.chr15.day07
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn parse-line [line]
	(let [variables (map keyword (re-seq #"[a-z]+" line))]
		(hash-map 
			:target (last variables)
			:number (first (map t/parse-int (re-seq #"\d+" line)))
			:instruction (first (re-seq #"[A-Z]+" line))
			:inputs (butlast variables))))

(defn init [input]
	(let [split-inputs (->> (map parse-line (str/split-lines input))
		 (group-by #(nil? (:inputs %))))
		  assignments (split-inputs true)
		  instructions (into #{} (split-inputs false))]
		(->> (hash-map 
		 	:values (zipmap (map :target assignments)(map :number assignments))
		 	:instructions instructions))))

(defn execute [{:keys [target instruction number] :as instructions} inputs]
	(cond 
		(= instruction "AND") (apply bit-and (remove nil? (conj inputs number)))
		(= instruction "OR") (apply bit-or inputs)
		(= instruction "LSHIFT") (bit-shift-left (first inputs) number)
		(= instruction "RSHIFT") (bit-shift-right (first inputs) number)
		(= instruction "NOT") (bit-not (first inputs))
		:rest (first inputs)))

(defn apply-instructions [{:keys [instructions values] :as status} {:keys [target inputs instruction number] :as next-inst}]
	(-> (update status :values assoc target (execute next-inst (map values inputs)))
		(update :instructions disj next-inst)))

(defn tick [{:keys [instructions values] :as status}]
	(->> (filter #(every? values (:inputs %)) instructions)
		 first
		 (apply-instructions status)))

(defn day07 [input which]
	(->> (init input)
		 (iterate tick)
		 (drop-while #(not= #{} (:instructions %)))
		 first
		 :values
		 which))

(defn day07b [input which]
	(->> (init input)
		 (#(update % :values assoc :b 3176))
		 (iterate tick)
		 (drop-while #(not= #{} (:instructions %)))
		 first
		 :values
		 which))

