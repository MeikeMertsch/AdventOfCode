(ns christmas.day08)


(defn deal-with-0 [input]
	(let [candidate (last (split-with #(not= 0 %) input))]
		(if (= (+ 2 (second candidate)) (count candidate))
			      (vec (concat (drop-last (count candidate)	input)
			      			   [(vec candidate)]))
			input)))

(defn hasNoSeq? [candidates]
	(or (= (count candidates) 1)
		(even? (count candidates))))

(defn seq-cand [input]
	(let [temp (take-last 3 (partition-by class input))
		  cand (vector (vec (take-last 2 (first temp))) (second temp) (last temp))
		  count-seq (peek (pop (first cand)))
		  count-num (peek (first cand))]
        (if (and (= count-seq (count (second cand)))
		 	     (= count-num (count (last cand))))
		 	(conj (vec (drop-last (count (apply concat cand)) input))
		 			   (vec (apply concat cand)))
		 	input)))

(defn deal-with-seq [input]
	(let [candidates (partition-by class input)]		
		(if (hasNoSeq? candidates)
			input
			(seq-cand input))))

(defn check [input]
	(cond 
		(contains? (into #{} (pop input)) 0) (deal-with-0 input)
		(not (hasNoSeq? (partition-by class input))) (seq-cand input)
		:else input))

(defn build-tree [input number]
	(check (conj input number)))

(defn exercise08 [input]
	(->> (reduce build-tree [] input)
		 last
		 (tree-seq coll? #(rest (rest %)))
		 (remove coll?)
		 (apply +)))

(defn something-smart [x y]
	(<= y (count (second x))))

(defn node-has-no-kids? [my_node]
	(= 0 (first my_node)))

(defn node-without-kids [my_node]
	(apply + (nnext my_node)))

(defn node-with-kids [my_node]
	(let [parts (partition-by class my_node)]
		 (->> (filter #(something-smart parts %) (last parts))
		      (map #(nth (second parts) (dec %)))
		      (#(map (fn [x] (if (node-has-no-kids? x) 
		                         (node-without-kids x)
		      					 (node-with-kids x))) %))
		      (apply +))))

(defn exercise08b [input]
	(->> (reduce build-tree [] input)
		 last
		 node-with-kids))