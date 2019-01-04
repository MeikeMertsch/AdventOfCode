(ns christmas.day20c
	(:require [clojure.string :as str]))

(def dir {\S [0 1] \W [-1 0] \E [1 0] \N [0 -1]})

(defn group-input [input]
	(->> (partition-by #{\( \) \|} input)
		 (map (partial apply str))))


(defn step [origin direction]
	(map + (dir direction) origin))

(defn determine-distances [all-distances [pos distance]]
	(assoc  all-distances 
			pos 
			(min distance 
			  	 (all-distances pos 10000000))))

(defn walk-directions [all-distances origin directions]
	(->> (reductions step origin directions)
		 (#(map vector % (drop (all-distances origin) (range))))
		 (reduce determine-distances all-distances)))

(defn get-origin [position]
	(->> (frequencies position)
		 (#(vector (- (% \E 0)(% \W 0)) (- (% \S 0)(% \N 0))))))

(defn walk [{:keys [idx ways all-dist position] :as result} instructions]
	(-> (update-in result [:all-dist] (constantly (walk-directions all-dist (get-origin position) instructions)))
		(update-in [:position] str instructions)))

(defn open-alternate [{:keys [idx ways position] :as results}]
	(-> (update-in results [:position] (constantly (ways idx)))
		(update-in [:alternatives idx] conj position)))

(defn open-sub [{:keys [idx ways position] :as results}]
	(-> (update-in results [:ways] assoc (inc idx) position)
		(update-in [:alternatives] assoc (inc idx) [])
		(update-in [:idx] inc)))

(defn close [{:keys [idx alternatives] :as results}]
	(-> (update-in results [:ways] dissoc idx)
		(update-in [:position] #(first (sort-by count > (conj (alternatives idx) %))))
		(update-in [:alternatives] dissoc idx)
		(update-in [:idx] dec)))

(defn tick [result instructions]
	(cond
		(str/starts-with? instructions "(") (open-sub result)
		(str/starts-with? instructions "|") (open-alternate result)
		(str/starts-with? instructions ")") (first (drop (count instructions) (iterate close result)))
		:rest (walk result instructions)))

(def step-0 {:idx 0 :ways {} :all-dist {[0 0] 0} :position "" :alternatives {}})

(defn day20 [instructions]
	(->> (group-input instructions)
		 (reduce tick step-0)
		 :all-dist
		 (sort-by last)
		 last
		 last))


(defn day20b [instructions]
	(->> (group-input instructions)
		 (reduce tick step-0)
		 :all-dist
		 (filter #(<= 1000 (last %)))
		 count))












