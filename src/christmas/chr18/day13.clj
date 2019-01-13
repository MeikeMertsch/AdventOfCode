(ns christmas.chr18.day13)

(def speeds {\> [1 0] \< [-1 0] \^ [0 -1] \v [0 1]})
(def dirs {:left :straight :straight :right :right :left})

(defn go-on [cart]
	(update-in cart [:pos] #(map + %1 %2) (:spd cart)))

(defn do-slash [cart]
    (->> (update-in cart [:spd] #(map - %2 (reverse %1)) [0 0])
      	  go-on))	

(defn do-backslash [cart]
    (->> (update-in cart [:spd] reverse)
      	  go-on))	

(defn next-dir [dir]
	(dirs dir))

(defn turn [spd way cart]
	(->> (zipmap spd way)
		 (#(% 0))
		 (#(% cart))))

(defn do-crossing [cart]
	(->> (update-in cart [:dir] next-dir)
		 (#(cond
		 	(= :straight (:dir %)) (go-on %)
		 	(= :left (:dir %)) (turn (:spd cart) [do-backslash do-slash] %)
		 	(= :right (:dir %)) (turn (:spd cart) [do-slash do-backslash] %)))))

(defn cart-row [row-nr row]
	(->> (map-indexed 
			#(vector [%1 row-nr] 
					 (hash-map  :spd (get speeds %2)
					 			:dir :right
					 			:pos [%1 row-nr]))
			row)
		 (remove #(= nil (:spd (last %))))))

(defn rules-row [row-nr row]
	(->> (map-indexed 
			#(vector [%1 row-nr] 
					 (get {\\ "`" \/ "/" \+ "+"} %2)) 
			row)
		 (remove #(= nil (last %)))))

(defn get-carts [board]
	(->> (mapcat #(cart-row % (nth board %)) (range 0 (count board)))
		 (apply concat)
		 (apply assoc {})))
		
(defn get-rules [board]
	(->> (mapcat #(rules-row % (nth board %)) (range 0 (count board)))
		 (apply concat)
		 (apply assoc {})))
		
(defn move [cart carts rule]
	(cond
		(nil? rule) (go-on (carts cart))
		(= "+" rule) (do-crossing (carts cart))
		(= "`" rule) (do-backslash (carts cart))
		(= "/" rule) (do-slash (carts cart))))

(defn check-crash [moved-cart carts]
	(let [pos (:pos moved-cart)]
	(if (nil? (carts pos))
		(assoc carts (:pos moved-cart) moved-cart)
		(dissoc carts (:pos moved-cart)))))

(defn tick [cart carts rules]
	(let [rule (rules cart)
		  reduced-carts (dissoc carts cart)
		  moved-cart (move cart carts rule)]
		(check-crash moved-cart reduced-carts)))

(defn order-positions [positions]
;	(println positions)
	(->> (sort-by first positions)
		 (sort-by last)))

(defn one-tick [ordered carts rules]
	(->> (reduce #(tick %2 %1 rules) carts ordered)
	
	))

(defn something [input]
	(let [carts-init (get-carts input)
		  rules (get-rules input)]
		(->> (reductions (fn [[carts _] _] (vector (one-tick (order-positions (keys carts))
											carts
											rules)
								  (count carts))) 
						  [carts-init 1000] 
						  (range))
		(take-while #(< 1 (second %)))
		last
		ffirst
		first

		;     (drop-while #(< 1 (count (first %))))
			; (drop-while #(nil? (:crash (first %))))
			; ffirst
			; :crash

	)))








