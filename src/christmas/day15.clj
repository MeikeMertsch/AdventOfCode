(ns christmas.day15
	(:require [clojure.set :as sets]))

(defn best-position [positions]
	(->> (map butlast positions)
		 (sort-by #(first (second %)))
		 (sort-by #(last (second %)))
		 (sort-by #(first (last %)))
		 (sort-by #(last (last %)))
		 first
		 second)) ; juxt

(defn order-positions [positions]
	(->> (sort-by first positions)
		 (sort-by last)))

(defn input-row [row-nr row obj]
	(->> (map #(vector % row-nr) (range (count row)))
		 (#(zipmap % row))
		 (filter #(= obj (val %)))
		 keys))

(defn find-obj [input obj]
	(->> (mapcat #(input-row % (nth input %) obj) (range 0 (count input)))
		 (into #{})))

(defn get-elves [input]
	(find-obj input \E))

(defn get-goblins [input]
	(find-obj input \G))

(defn get-free [input]
	(find-obj input \.))

(defn dec-creatures [creatures amount]
	(zipmap creatures (repeat amount)))

(defn sorted-neighbours [[x y]]
	(vector [x (dec y)]
			[(dec x) y]
			[(inc x) y]
			[x (inc y)]))

(defn next-step [prev free enemy-pos]
	(->> (sorted-neighbours (last prev))
		 (filter #(or (contains? enemy-pos %)
		 			  (contains? free %)))
		 (remove (set prev))))

(defn next-steps [prev free enemy-pos]
	(map #(conj prev %) (next-step prev free enemy-pos)))

(defn ways-with-enemies [ways enemy-pos]
	(filter #(enemy-pos (last %)) ways))

(defn walk-towards-neighbour [[poss untested x targets]]
;	(println poss x targets)
	(let [neighs (mapcat sorted-neighbours poss)
		  reachable (filter untested neighs)]

;(println (vector reachable (reduce disj untested reachable)
;				 (filter targets neighs) targets))

		  (vector reachable
		  		  (reduce disj untested reachable)
		  		  (filter targets neighs)
		  		  targets)))

(defn will-move [pos free enemy-pos]
	(and (not (some enemy-pos (sorted-neighbours pos)))
		(->> (iterate walk-towards-neighbour [[pos] free [] enemy-pos])
			 (drop-while #(and  (nil? (nth % 2))
		 						(not-empty (first %))))
		 	 first
		 	 (#(or (not (empty? (first %)))
		 	 	   (not (nil? (nth % 2))))))))

(defn choose-target [pos free targets]
;	(println "choose-target" pos targets)
    (if (nil? pos)
        nil
	(->> (#(iterate walk-towards-neighbour [[pos] free [] targets]))
			 (drop-while #(and  (empty? (nth % 2))
		 						;(not (nil? (first %)))
		 						(not-empty (first %))))
		 	 first
		 	 (#(nth % 2))
		 	 order-positions
		 	 first)))



(defn order-second [positions]
	(->> (sort-by #(first (second %)) positions)
		 (sort-by #(last (second %)))))

(defn reduced-next-steps [free enemy-pos positions]
	(->> (mapcat #(next-steps % free enemy-pos) positions)
		 (group-by last)
		 (map last)
		 (map order-second)
		 (map first)))

(defn next-pos [pos free enemy-pos] ; can be nil!
	(->> (iterate #(reduced-next-steps free enemy-pos %) [[pos]])
		 (map #(ways-with-enemies % enemy-pos))
		 (take 40);(count free)); this!!
		 (drop-while empty?)
		 first
		 best-position))

(defn next-pos-2 [pos free enemy-pos]
;(println "next-pos-2" pos enemy-pos)
	(let [neighs (sorted-neighbours pos)]
;(println "next-pos-2-let" pos neighs (some enemy-pos neighs))
		(if (some enemy-pos neighs)
			nil
			(-> (choose-target pos free enemy-pos)
				(choose-target free (into #{} (filter free neighs)))
		))))


(defn move [pos n-pos friends board]
	(let [creature ((friends board) pos)]
		(-> (update-in board [:free] disj n-pos)
			(update-in [:free] conj pos)
			(update-in [friends] assoc n-pos creature)
			(update-in [friends] dissoc pos))))

(defn victim [pos enemies board]
;(println "victim" pos enemies board)
	(->> (map #(find (enemies board) %) (sorted-neighbours pos)) 
		 (remove nil?)
		 (sort-by last)
		 ffirst)) 

(defn remove-bodies [pos enemies board]
	(let [body? (< ((enemies board) pos) 1)]
		(if body?  (->> (update-in board [enemies] dissoc pos)
						(#(update-in % [:free] conj pos)))
		 			board)))

(defn attack [pos enemies board]
	(->> (update-in board [enemies pos] - 3)
		 (remove-bodies pos enemies)))

(defn take-turn [pos friends enemies board]
	(let [enemy-pos (into #{} (keys (enemies board)))
		  moves (will-move pos (:free board) enemy-pos)]
;		  (println moves)
		(->> (if moves 
			     (let [m-pos (next-pos-2 pos (:free board) enemy-pos)]
			     	;(if (some? m-pos)
				 	(vector m-pos (move pos m-pos friends board)))
				 	;(vector pos board))
				 (vector pos board))	
			 (#(let [board (last %)
			 		 n-pos (first %)
			 		 a-pos (victim n-pos enemies board)]
;(println n-pos a-pos)			
			 (if (some? a-pos)
			 	 (attack a-pos enemies board)
			 	 board))))))

(defn take-turn-2 [pos friends enemies board]
;(println "take-turn-2" pos friends)
	(let [enemy-pos (into #{} (keys (enemies board)))
		  m-pos (next-pos-2 pos (:free board) enemy-pos)
		  a-pos (victim (some identity [m-pos pos]) enemies board)]
;(println "take-turn-2 let" pos enemy-pos m-pos a-pos)
		(->> (if m-pos
				 (move pos m-pos friends board)
				 board)
			 (#(if a-pos
			 		(attack a-pos enemies %)
			 		%))

		)))



(defn tick [pos board]
;	(println pos)
;(time
    (cond ((into #{} (keys (:elves board))) pos)
    	(take-turn-2 pos :elves :goblins board)
    	  ((into #{} (keys (:goblins board))) pos)
    	(take-turn-2 pos :goblins :elves board)
    	  :rest board))

(defn play-round [board]
#_((println "play-round")
	(println :elves (:elves board))
	(println :goblins (:goblins board)))
;	(println :free (:free board))
;(time
	(->> (mapcat board [:elves :goblins])
		 (map first)
		 order-positions
		 (reduce #(tick %2 %1) board)))

(defn create-board [input]
	{:elves (dec-creatures (get-elves input) 200) 
	 :goblins (dec-creatures (get-goblins input) (/ (* 3 200) 3)) 
	 :free (get-free input)})

(defn hp [board]
	(->> (merge (:elves board) (:goblins board))
		 (map val)
		 (apply +)
		 ))

(defn play [input]
	;(println (play-round (create-board input)))
	(->> (iterate play-round (create-board input))
		(split-with #(and (< 0 (count (:elves %)))
		 				   (< 0 (count (:goblins %)))))
		 (#(vector (count (first %))
		 		   (hp (first (last %)))
		 		   (* (count (first %)) (hp (first (last %))))
		 			:goblins
		 		   (:goblins (first (last %)))
		 		    :elves
		 		   (:elves (first (last %)))
		 		   )) )


		)




