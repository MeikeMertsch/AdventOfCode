(ns christmas.chr18.day09)

; all 7 (!) revisions of my code on this one on https://gist.github.com/MeikeMertsch/f7e5c76929bf0036850110ca79c63a0e

(defn point-calculation [points player number old-marble]
	(assoc points player (+ number
							old-marble
							(get points player 0))))

(defn calc-points [points player number [front back]]
	(let [x (count front)
		  y (count back)
		  jump (- x 7)]
		(if (neg? jump)
			[(point-calculation 
				points 
				player 
				number 
				(first (drop (+ jump y) back)))
			 [(apply conj front (take (+ jump y) back))
			  (drop (+ (inc jump) y) back)]]		
			 
			[(point-calculation 
				points
				player 
				number
				(peek (subvec front jump (inc jump))))
			 [(subvec front 0 jump)
			  (apply conj back (reverse (subvec front (inc jump))))]])))

(defn insert-a-marble [points number [front back]]
		[points
		 (if (= 1 (count back))
		 	[[0]
		  	 (conj (concat (rest front) 
		  	 	   		   back)
		  	 	   number)]
		  	[(apply conj front (take 2 back))
		  	 (conj (drop 2 back) number)])])

(defn next-circle [players number data]
	(let [points (first data)
		  pos (second data)
		  player (rem number players)
		  circle (last data)]
	(if (= 0 (rem number 23)) 
		(calc-points points player number circle)
        (insert-a-marble points number circle))))

(defn do-all-moves [players marbles]
	(reduce #(next-circle players %2 %1) [{} [[0] '(1)]] 
              (range 2 (inc marbles))))
		 
(defn day09 [players marbles]
	(->> (do-all-moves players marbles)
		 first
		 vals
		 (apply max)))