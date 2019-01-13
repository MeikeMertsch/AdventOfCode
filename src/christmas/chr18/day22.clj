(ns christmas.chr18.day22)

(defn erosion-level [depth geo-idx]
	(mod (+ geo-idx depth) 20183))
	
(defn pixel [depth geo-idx]
	(->> (erosion-level depth geo-idx)
		 (#(vector % (mod % 3)))))

(defn row-0 [depth columns]
	(->> (range)
		 (take (inc columns))
		 (map (partial * 16807))
		 (map (partial pixel depth))))

(defn px-0 [depth row-nr]
	(pixel depth (* row-nr 48271)))	

(defn next-px [[x y] row-nr depth prev top]
	(let [multiplier (if (= y row-nr) (concat (take x (repeat 1)) [0] (repeat 1)) (repeat 1))]
	(->> (map * top multiplier)
		 (#(map first [prev %]))
		 (apply *)
		 (pixel depth))))

(defn next-row [[x y :as target] depth previous row-nr]
	(let [multiplier (if (= y row-nr) (concat (take x (repeat [1 1])) [[0 0]] (repeat [1 1])) (repeat [1 1]))
		 ; a (println multiplier)
		  prev (map (partial map *) previous multiplier)
		  ]
		 ; (println (take 15 multiplier))
	(reductions (partial next-px target row-nr depth) 
				(px-0 depth row-nr) 
				(rest prev))))
	
(defn all-pixels [depth rows columns target]
	(reductions (partial next-row target depth)
				(row-0 depth columns)
				(range 1 (inc rows))))
	
(defn day22a [depth rows columns]
	(->> (all-pixels depth rows columns [columns rows])
		 (apply concat)
		 butlast
		 (map last)
		 (apply +)
		 (+ (mod (erosion-level depth 0) 3))))

(defn terrain [depth rows columns target]
	(->> (all-pixels depth rows columns target)
		 (mapv #(mapv last %))
		 (#(update-in % [rows columns] (constantly 0)))))

(defn transform-to-map [depth rows columns target]
	(->> (terrain depth rows columns target)
		 (map-indexed (fn [row-nr row] (map-indexed (fn [column-nr value] (vector [column-nr row-nr] value)) row)))
		 (apply concat)
		 (apply concat)
		 (apply hash-map)))

(defn valid-neighbours [{:keys [perms temps tool maxima]} cave [[x y] minutes]]
	(let [new-time (inc minutes)
		 [max-x max-y] maxima]
		(->> (hash-map [(dec x) y] new-time
					   [x (dec y)] new-time
				 	   [(inc x) y] new-time
					   [x (inc y)] new-time)
			 (filter (fn [[spot t]] (and (<= 0 (first spot) max-x)
			 			   			(<= 0 (last spot) max-y)
			 			   			(not= (last tool) (cave spot))
			 			   			(if-let [perm-time (perms spot)] (<= new-time perm-time) true)
			 			   			(if-let [temp-time (temps spot)] (< new-time temp-time) true))))
			 (apply concat)
			 (apply hash-map))))

(defn tick [{:keys [perms temps candidates tool cave] :as status}]
	(let [candidate (first (sort-by last candidates))
		  neighbours (valid-neighbours status cave candidate)]
		(-> (update-in status [:temps] (partial apply assoc) candidate)
			(update-in [:candidates] dissoc (first candidate))
			(update-in [:candidates] (partial merge-with min) neighbours))))

(defn spread [status]
	(->> (iterate tick status)
		 (drop-while #(not-empty (:candidates %)))
		 first))

(defn map-kv [f coll]
  (reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))

(defn unsuitable-candidates [status tool]
	(->> (:candidates status)
		 keys
		 (filter #(= tool ((:cave status) %)))))

(defn switch-tool [status tool]
	(update-in status [:tool] #(conj % tool)))

(defn next-tools [status]
	(->> (range 3)
		 (remove (hash-set (last (:tool status))))))

(defn all-tool-changes [{:keys [perms temps tool] :as status}]
	(-> (update-in status [:perms] (partial merge-with min) temps)
		(#(map (partial switch-tool %) (next-tools status)))))

(defn sort-out-cands [status]
	(-> (assoc status :candidates (:perms status))
		(update-in [:candidates] (partial apply dissoc) (unsuitable-candidates status (last (:tool status))))
		(update-in [:candidates] (partial map-kv (partial + 7)))))

(defn new-perms [[tool all-status]]
	(->> (map :perms all-status)
		 (apply (partial merge-with min))
		 (hash-map :tool tool :temps {} :perms)
		 sort-out-cands))

(defn add-reached [target status]
	(->> (if-let [reached (= 1 (last (:tool status)))]
			((:perms status) target) nil)
		 (assoc status :reached)))

(defn work-old [target status]
	(->> (mapcat #(all-tool-changes (spread %)) status)
		 (map #(dissoc % :cave :maxima :temps :candidates))
		 (group-by #(last (:tool %)))
		 (map new-perms)
		 ;(map #(assoc % :maxima (:maxima (first status)) :cave (:cave (first status))))
		 ;(map (partial add-reached target))
		 time))

(defn work [target status]
	(->> (mapcat #(all-tool-changes (spread %)) status)
		 (map #(assoc % :temps {}))
		 (map sort-out-cands)
		 (map (partial add-reached target))
		 time))

(defn day22b [status target]
	(let [maxima (last (sort-by (juxt last first) (keys (:cave status))))]
		(->> (assoc status :maxima maxima)
			 vector
			 (iterate (partial work target))
			 (drop 2)
			 (drop-while #(every? nil? (map :reached %)))
			 first
			 (filter #(some? (:reached %)))

			 (map #(vector (:reached %) (:tool %)))
			)))



