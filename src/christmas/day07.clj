(ns christmas.day07
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))


(defn find-candidates [remaining restrictions]
	(->> (map last restrictions)
		 distinct
		 (reduce #(remove (fn [x] (= %2 x)) %1) remaining)
		 sort))

(defn find-next [remaining restrictions]
	(first (find-candidates remaining restrictions)))


(defn exercise07 [allofthem file]
	(loop [remaining allofthem
		   result []
		   restrictions file]
		   (let [newestResult (find-next remaining restrictions)]
  		(if (empty? remaining)
  			  (apply str result)
   			  (recur (remove #(= % newestResult) remaining)
   			  		 (conj result newestResult)
   			  		 (remove #(= (first %) newestResult) restrictions))))))
   			  	

;------------- Part II

(defn duration [masterpiece secs]
	(- (int (first masterpiece)) (- 64 secs)))

(defn tick [workers]
	(reduce #(assoc %1 (first %2) (dec (last %2))) workers workers))

(defn free-up-workers [workers finishedwork]
	(apply dissoc workers finishedwork))

(defn finish-work [workers]
	(map first (filter #(= 1 (val %)) workers)))

(defn time-candidates [candidates secs]
	(map #(vector % (duration % secs)) candidates))

(defn get-to-work [elves workers timedcandidates] 
	(->> (- elves (count workers))
         (#(take % timedcandidates))
         (reduce #(apply assoc %1 %2) workers)))

(defn tcandidates [remaining restrictions secs]
	(->> (find-candidates remaining restrictions)
		 (#(time-candidates % secs))))

(defn loosen-restrictions [restrictions finishedwork]
	(reduce #(remove (fn [x] (= %2 (first x))) %1) restrictions finishedwork))

(defn throw-out-finished [remaining finishedwork newworkers]
	(->> (apply disj remaining finishedwork)
		 (into #{})
		 (#(apply disj % newworkers))))

(defn thingy [[elves restrictions workers remaining secs] newtime]
	(let [finishedwork (finish-work workers)
		  newrestrictions (loosen-restrictions restrictions finishedwork)
		  newremainders (throw-out-finished remaining finishedwork (keys workers))
		  newworkers (->> (tick workers)
		  	              (#(free-up-workers % finishedwork))
		    	 		  (#(get-to-work elves % (tcandidates newremainders newrestrictions secs))))]
	(vector elves
			newrestrictions
		    newworkers
		    newremainders
		    secs)))

(defn exercise07b [restrictions elves remaining secs] 
	(->> (range)
         (reductions thingy [elves restrictions {} remaining secs])
         (drop 1)
         (take-while #(not (empty? (nth % 2))))
         count))



