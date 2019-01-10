(ns christmas.chr15.day24
  (:require [clojure.string :as str]))

(defn compartment-weight [presents compartments]
	(/ (apply + presents) compartments))

(defn new-combo [all-objects previous]
	(for [x all-objects
		  y previous
		  :let [result (conj y x)]
		  :when (apply distinct? result)
		  :when (apply < result)]
		result))

(defn has-proper-weight? [weight parcels]
	(= (apply + parcels)
		weight))

(defn quantum-entanglement [parcels]
	(apply * parcels))

(defn packing [parcels weight]
	(->> (iterate (partial new-combo parcels) (map vector parcels))
		 (drop-while (partial not-any? #(has-proper-weight? weight %)))
	 	 first
	 	 (filter (partial has-proper-weight? weight))
	 	 (sort-by quantum-entanglement)
	 	 first
	 	 quantum-entanglement))

(defn day24 [parcels]
	(packing parcels (compartment-weight parcels 3)))

(defn day24b [parcels]
	(packing parcels (compartment-weight parcels 4)))


