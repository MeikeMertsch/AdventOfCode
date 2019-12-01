(ns christmas.chr16.day13
	   (:require [clojure.string :as str]))

(defn valid? [blocked [x y :as value]]
    (and (<= 0 x)
         (<= 0 y)
         (not (contains? blocked value))))

(defn neighbors[value]
				(map (partial map +) (repeat value) [[0 1][1 0][0 -1][-1 0]]))

(defn find-possibilities[{:keys [actual blocked]}]
    (->> (map neighbors actual)
         (apply concat)
         (filter (partial valid? blocked))))

(defn way? [fav [x y]]
    (->> (+ (* x x) (* 3 x) (* 2 x y) y (* y y) fav)
							  Integer/toBinaryString
							  (filter (partial = \1))
							  count
							  even?))      

(defn tick [{:keys [fav] :as game}]
				(let [poss (find-possibilities game)
				      ways (filter (partial way? fav) poss)]
								(-> (update-in game [:blocked] #(reduce conj % poss))
								    (assoc :actual ways)
								    (update-in [:ways] #(reduce conj % ways)))))

(defn not-goal[{:keys [blocked goal]}]
				(not (contains? blocked goal)))

(defn walk [input goal]
    (->> {:actual [[1 1]] :blocked #{} :fav input :goal goal :ways #{[1 1]}}
    					(iterate tick)
    					(take-while not-goal)
    					count))


(defn walk2 [input goal]
    (->> {:actual [[1 1]] :blocked #{} :fav input :goal goal :ways #{[1 1]}}
    					(iterate tick)
    					(drop 50)
    					first
    					:ways
    					count))


